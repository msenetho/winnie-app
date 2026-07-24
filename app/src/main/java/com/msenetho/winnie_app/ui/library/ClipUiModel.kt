package com.msenetho.winnie_app.ui.library

import com.msenetho.winnie_app.domain.model.VoiceClip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlin.collections.emptyList

class ClipUiModel {
    private val _clips = MutableStateFlow<List<VoiceClip>>(emptyList())
    private val _isLoading = MutableStateFlow(true)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _currentPlayingClipId = MutableStateFlow<Int?>(null)
    private val _selectedMode = MutableStateFlow(ViewMode.LIST)

    private val clipUiModels = combine(
        _clips,
        favouritesRepository.favouriteIds,
        _currentPlayingClipId
    ) { clips, favouriteIds, currentlyPlayingClipId ->

    }
}