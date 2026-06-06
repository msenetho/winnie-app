package com.msenetho.winnie_app.core.audio

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.core.net.toUri

class MediaAudioPlayer(
    context: Context
) : AudioPlayer {
    private val player = ExoPlayer.Builder(context).build()

    override fun playAsset(assetPath: String) {
        val uri = "asset:///$assetPath".toUri()
        val mediaItem = MediaItem.fromUri(uri)

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun stop() {
        player.stop()
    }

    override fun release() {
        player.release()
    }
}