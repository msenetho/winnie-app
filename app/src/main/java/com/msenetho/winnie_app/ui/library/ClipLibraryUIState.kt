package com.msenetho.winnie_app.ui.library

import com.msenetho.winnie_app.domain.model.VoiceClip

data class ClipLibraryUIState (
    val clips: List<VoiceClip> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)