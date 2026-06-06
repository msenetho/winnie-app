package com.msenetho.winnie_app.ui.customspeech

data class CustomSpeechUIState(
    val prompt: String = "",
    val errorMessage: String? = null,
    val isGenerating: Boolean = false,
    val maxChar: Int = 250
) {
    val remainingChar: Int
        get() = maxChar - prompt.length

    val canGenerate: Boolean
        get() = prompt.isNotBlank() &&
                errorMessage == null &&
                !isGenerating
}