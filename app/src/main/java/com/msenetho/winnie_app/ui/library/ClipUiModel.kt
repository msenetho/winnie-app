package com.msenetho.winnie_app.ui.library

import com.msenetho.winnie_app.domain.model.VoiceClip

data class ClipUiModel (
    val clip: VoiceClip,
    val isFavourite: Boolean,
    val isPlaying: Boolean
)