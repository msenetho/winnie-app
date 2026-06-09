package com.msenetho.winnie_app.core.audio

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.core.net.toUri
import androidx.media3.common.PlaybackException
import java.io.File

class MediaAudioPlayer(
    context: Context
) : AudioPlayer {
    private val player = ExoPlayer.Builder(context).build()

    override var onPlaybackEnded: (() -> Unit)? = null

    init {
        player.addListener(
            object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    Log.e("MediaAudioPlayer", "Playback error", error)
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    Log.d("MediaAudioPlayer", "isPlaying: $isPlaying")
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        onPlaybackEnded?.invoke()
                    }
                }
            }
        )
    }

    override fun playAsset(assetPath: String) {
        val uri = "asset:///$assetPath".toUri()
        val mediaItem = MediaItem.fromUri(uri)

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun playFile(file: File) {
        val mediaItem = MediaItem.fromUri(Uri.fromFile(file))

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