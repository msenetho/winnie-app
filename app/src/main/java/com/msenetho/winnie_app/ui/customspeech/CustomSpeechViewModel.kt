package com.msenetho.winnie_app.ui.customspeech

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CustomSpeechViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CustomSpeechUIState())
    val uiState: StateFlow<CustomSpeechUIState> = _uiState.asStateFlow()

    fun onPromptChanged(newPrompt: String) {
        val maxChar = _uiState.value.maxChar
        val limitedPrompt = newPrompt.take(maxChar)

        _uiState.value = _uiState.value.copy(
            prompt = limitedPrompt,
            errorMessage = validatePrompt(limitedPrompt)
        )
    }

    fun onCreateClicked() {
        val state = _uiState.value

        if (!state.canGenerate) return

        Log.d(
            "CustomSpeechViewModel",
            "Create tapped, Prompt Length: ${state.prompt.trim().length}"
        )

        // TTS repo here
    }

    private fun validatePrompt(prompt: String): String? {
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

@Composable
fun CustomSpeechRoute(
    viewModel: CustomSpeechViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    CustomSpeechScreen(
        uiState = uiState,
        onPromptChanged = viewModel::onPromptChanged,
        onCreateClicked = viewModel::onCreateClicked
    )
}