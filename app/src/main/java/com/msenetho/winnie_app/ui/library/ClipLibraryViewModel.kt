package com.msenetho.winnie_app.ui.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.msenetho.winnie_app.core.audio.AudioPlayer
import com.msenetho.winnie_app.core.audio.MediaAudioPlayer
import com.msenetho.winnie_app.data.clip.AssetClipDataSource
import com.msenetho.winnie_app.data.favourites.FavouritesRepository
import com.msenetho.winnie_app.domain.model.VoiceClip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

class ClipLibraryViewModel(
    application: Application, private val favouritesRepository: FavouritesRepository
) : AndroidViewModel(application) {

    private val _clips = MutableStateFlow<List<VoiceClip>>(emptyList())
    private val _isLoading = MutableStateFlow(true)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _currentlyPlayingClipId = MutableStateFlow<Int?>(null)
    private val _selectedMode = MutableStateFlow(ViewMode.LIST)

    private val _uiState = MutableStateFlow(ClipLibraryUIState())
    val uiState: StateFlow<ClipLibraryUIState> = _uiState.asStateFlow()
    private val audioPlayer: AudioPlayer = MediaAudioPlayer(application).apply {
        onPlaybackEnded = {
            _currentlyPlayingClipId.value = null
        }
    }

    init {
        loadClips()
    }

    // combine #1
    private val clipUiModels: Flow<List<ClipUiModel>> = combine(
        _clips,
        favouritesRepository.favouriteIds,
        _currentlyPlayingClipId
    ) { clips, favouriteIds, currentlyPlayingClipId ->
        clips.map { clip ->
            ClipUiModel(
                clip = clip,
                isFavourite = clip.id in favouriteIds,
                isPlaying = (clip.id == currentlyPlayingClipId)
            )
        }
    }

    // combine #2
    private val combinedUiState: Flow<ClipLibraryUIState> = combine(
        clipUiModels,
        _isLoading,
        _errorMessage,
        _selectedMode
    ) { clips, isLoading, errorMessage, selectedMode ->
        ClipLibraryUIState(
            clips = clips,
            isLoading = isLoading,
            errorMessage = errorMessage,
            selectedMode = selectedMode
        )
    }

    private fun loadClips() {
        try {
            val clips = AssetClipDataSource(getApplication()).loadVoiceClips()
            _clips.value = clips
            _isLoading.value = false

        } catch (_: Exception) {
            _isLoading.value = false
            _errorMessage.value = "Could not load voice clips"
        }
    }

    fun onClipClicked(clip: VoiceClip) {
        audioPlayer.playAsset(clip.assetPath)
        _currentlyPlayingClipId.value = clip.id
    }

    fun onStopClicked() {
        audioPlayer.stop()
        _currentlyPlayingClipId.value = null
    }

    override fun onCleared() {
        audioPlayer.release()
    }

    fun onViewModeChanged(mode: ViewMode) {
        _selectedMode.value = mode
    }
}