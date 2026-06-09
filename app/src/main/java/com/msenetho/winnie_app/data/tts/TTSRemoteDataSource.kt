package com.msenetho.winnie_app.data.tts

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

class TTSRemoteDataSource (
    private val context: Context,
    private val client: OkHttpClient = OkHttpClient()
) {
    suspend fun generateSpeech(text: String): File = withContext(Dispatchers.IO) {
        val json = JSONObject()
            .put("text", text)
            .toString()

        val requestBody = json.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("http://10.0.2.2:3001/tts")
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            response.close()
            throw IllegalStateException("TTS Request failed: ${response.code}")
        }

        val audioBytes = response.body.bytes()
        response.close()

        val outputFile = File(context.cacheDir, "generate_tts.mp3")
        outputFile.writeBytes(audioBytes)

        outputFile
    }
}