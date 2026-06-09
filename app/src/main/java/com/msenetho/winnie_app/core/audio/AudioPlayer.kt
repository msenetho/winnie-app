package com.msenetho.winnie_app.core.audio

import java.io.File

interface AudioPlayer {
    var onPlaybackEnded: (() -> Unit)?
    fun playAsset(assetPath: String)
    fun playFile(file: File)
    fun stop()
    fun release()
}