package com.msenetho.winnie_app.core.audio

interface AudioPlayer {
    var onPlaybackEnded: (() -> Unit)?
    fun playAsset(assetPath: String)
    fun stop()
    fun release()
}