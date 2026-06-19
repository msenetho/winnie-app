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

        val audioBytes = client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IllegalStateException("TTS request failed: ${response.code}")
            }

            response.body.bytes()
        }

        val outputFile = File(context.cacheDir, "generated_tts.mp3")
        outputFile.writeBytes(audioBytes)

        return@withContext outputFile
    }
}