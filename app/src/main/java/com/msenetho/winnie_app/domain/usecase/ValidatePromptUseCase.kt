package com.msenetho.winnie_app.domain.usecase

class ValidatePromptUseCase {
    operator fun invoke(prompt: String): String? {
        val trimmedPrompt = prompt.trim()

        return when {
            trimmedPrompt.isBlank() -> null
            trimmedPrompt.length < 2 -> "Enter at least 2 characters."
            prompt.any { Character.isISOControl(it) && it != '\n' && it != '\t' } ->
                "Text contains unsupported characters."

            else -> null
        }
    }
}