package com.msenetho.winnie_app.data.tts

import java.io.File

class TTSRepository(
    private val remoteDataSource: TTSRemoteDataSource
) {
    suspend fun generateSpeech(text: String): File {
        return remoteDataSource.generateSpeech(text)
    }
}