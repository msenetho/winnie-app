package com.msenetho.winnie_app.core.audio

interface AudioPlayer {
    fun playAsset(assetPath: String)
    fun stop()
    fun release()
}