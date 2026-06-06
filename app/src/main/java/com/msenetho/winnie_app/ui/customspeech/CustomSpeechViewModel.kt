package com.msenetho.winnie_app.ui.customspeech

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.msenetho.winnie_app.core.audio.AudioPlayer
import com.msenetho.winnie_app.core.audio.MediaAudioPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Duration.Companion.milliseconds

class CustomSpeechViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(CustomSpeechUIState())
    val uiState: StateFlow<CustomSpeechUIState> = _uiState.asStateFlow()
    private val audioPlayer: AudioPlayer = MediaAudioPlayer(application).apply {
        onPlaybackEnded = {
            _uiState.value = _uiState.value.copy(
                isPlaying = false
            )
        }
    }

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

        viewModelScope.launch {
            _uiState.value = state.copy(
                isGenerating = true,
                isPlaying = false
            )

            delay(1000.milliseconds)

            audioPlayer.playAsset("audio/Boowomp.mp3")

            _uiState.value = _uiState.value.copy(
                isGenerating = false,
                isPlaying = true
            )
        }
    }

    override fun onCleared() {
        audioPlayer.release()
        super.onCleared()
    }

    fun onStopClicked() {
        audioPlayer.stop()

        _uiState.value = _uiState.value.copy(
            isPlaying = false
        )
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
        onCreateClicked = viewModel::onCreateClicked,
        onStopClicked = viewModel::onStopClicked
    )
}