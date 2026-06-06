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

class ClipLibraryViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(ClipLibraryUIState())
    private val audioPlayer: AudioPlayer = MediaAudioPlayer(application)
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
        } catch (error: Exception) {
            _uiState.value = ClipLibraryUIState(
                isLoading = false,
                errorMessage = "Could not load voice clips"
            )
        }
    }

    fun onClipClicked(clip: VoiceClip) {
        audioPlayer.playAsset(clip.assetPath)
    }

    override fun onCleared() {
        audioPlayer.release()
        super.onCleared()
    }
}