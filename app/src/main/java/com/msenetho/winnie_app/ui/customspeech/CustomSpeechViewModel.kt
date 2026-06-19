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
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msenetho.winnie_app.data.tts.TTSRemoteDataSource
import com.msenetho.winnie_app.data.tts.TTSRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.msenetho.winnie_app.domain.usecase.ValidatePromptUseCase

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

    private val ttsRepo = TTSRepository(
        TTSRemoteDataSource(application)
    )

    private val validatePromptUseCase = ValidatePromptUseCase()

    fun onPromptChanged(newPrompt: String) {
        val maxChar = _uiState.value.maxChar
        val limitedPrompt = newPrompt.take(maxChar)

        _uiState.value = _uiState.value.copy(
            prompt = limitedPrompt,
            errorMessage = validatePromptUseCase(limitedPrompt)
        )
    }

    fun onCreateClicked() {
        val state = _uiState.value

        if (!state.canGenerate) return

        viewModelScope.launch {
            _uiState.value = state.copy(
                isGenerating = true,
                isPlaying = false,
                errorMessage = null
            )

            try {
                val audioFile = ttsRepo.generateSpeech(state.prompt.trim())

                audioPlayer.playFile(audioFile)

                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    isPlaying = true,
                    errorMessage = null
                )
            } catch (exception: Exception) {
                Log.e("CustomSpeechViewModel", "Speech generation failed", exception)

                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    isPlaying = false,
                    errorMessage = "Could not create speech."
                )
            }
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