package com.msenetho.winnie_app.ui.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.msenetho.winnie_app.core.audio.AudioPlayer
import com.msenetho.winnie_app.core.audio.MediaAudioPlayer
import com.msenetho.winnie_app.data.clip.AssetClipDataSource
import com.msenetho.winnie_app.domain.model.VoiceClip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ClipLibraryViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(ClipLibraryUIState())
    private val audioPlayer: AudioPlayer = MediaAudioPlayer(application).apply {
        onPlaybackEnded = {
            _uiState.value = _uiState.value.copy(
                currentlyPlayingClipId = null
            )
        }
    }
    val uiState: StateFlow<ClipLibraryUIState> = _uiState.asStateFlow()

    init {
        loadClips()
    }

    private fun loadClips() {
        try {
            val clips = AssetClipDataSource(getApplication()).loadVoiceClips()

            _uiState.value = ClipLibraryUIState(
                clips = clips,
                isLoading = false
            )
        } catch (_: Exception) {
            _uiState.value = ClipLibraryUIState(
                isLoading = false,
                errorMessage = "Could not load voice clips"
            )
        }
    }

    fun onClipClicked(clip: VoiceClip) {
        audioPlayer.playAsset(clip.assetPath)

        _uiState.value = _uiState.value.copy(
            currentlyPlayingClipId = clip.id
        )
    }

    fun onStopClicked() {
        audioPlayer.stop()

        _uiState.value = _uiState.value.copy(
            currentlyPlayingClipId = null
        )
    }

    override fun onCleared() {
        audioPlayer.release()
    }

    fun onViewModeChanged(mode: ViewMode) {
        _uiState.update {
            it.copy(selectedMode = mode)
        }
    }
}