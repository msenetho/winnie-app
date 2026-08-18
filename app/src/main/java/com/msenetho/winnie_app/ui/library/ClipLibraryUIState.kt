package com.msenetho.winnie_app.ui.library

data class ClipLibraryUIState (
    val clips: List<ClipUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedMode: ViewMode = ViewMode.LIST
)