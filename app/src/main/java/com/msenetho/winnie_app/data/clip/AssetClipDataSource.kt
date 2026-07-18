package com.msenetho.winnie_app.data.clip

import android.content.Context
import com.msenetho.winnie_app.domain.model.VoiceClip
import org.json.JSONArray

class AssetClipDataSource (
    private val context: Context
) {
    fun loadVoiceClips(): List<VoiceClip> {
        val jsonText = context.assets
            .open("voice_clips.json")
            .bufferedReader()
            .use{it.readText()}

        val jsonArray = JSONArray(jsonText)
        val clips = mutableListOf<VoiceClip>()

        for (index in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(index)

            clips.add(
                VoiceClip(
                    title = item.getString("title"),
                    assetPath = item.getString("assetPath"),
                    id = item.getInt("id")
                )
            )
        }

        return clips
    }
}