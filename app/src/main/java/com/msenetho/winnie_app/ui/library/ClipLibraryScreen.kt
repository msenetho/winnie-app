package com.msenetho.winnie_app.ui.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msenetho.winnie_app.domain.model.VoiceClip

@Composable
fun ClipLibraryScreen(
    clips: List<VoiceClip>,
    isLoading: Boolean,
    errorMessage: String?,
    currentlyPlayingAssetPath: String?,
    onClipClicked: (VoiceClip) -> Unit,
    onStopClicked: () -> Unit,
    selectedMode: ViewMode,
    onViewModeChanged: (ViewMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // title
        Text(
            text = "Quotes",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // grid/list button
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .align(Alignment.End)
                //.height(24.dp) // current height clips text
        ) {
            SegmentedButton(
                selected = selectedMode == ViewMode.LIST,
                onClick = { onViewModeChanged(ViewMode.LIST) },
                shape = SegmentedButtonDefaults.itemShape(
                    index = 0,
                    count = 2
                )
            ) {
                // placeholder for icon
                Text("List")
            }

            SegmentedButton(
                selected = selectedMode == ViewMode.GRID,
                onClick = { onViewModeChanged(ViewMode.GRID) },
                shape = SegmentedButtonDefaults.itemShape(
                    index = 1,
                    count = 2
                )
            ) {
                // placeholder for icon
                Text("Grid")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Text("Loading lines...")
            }

            errorMessage != null -> {
                Text(errorMessage)
            }

            else -> {
                when (selectedMode) {
                    ViewMode.LIST ->
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(clips) {clip ->
                                ClipListItem(
                                    clip = clip,
                                    isPlaying = clip.assetPath == currentlyPlayingAssetPath,
                                    onClipClicked = onClipClicked,
                                )
                            }
                        }

                    ViewMode.GRID ->
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 180.dp),
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(clips) { clip ->
                                ClipListItem(
                                    clip = clip,
                                    isPlaying = clip.assetPath == currentlyPlayingAssetPath,
                                    onClipClicked = onClipClicked,
                                )
                            }
                        }
                }

                Button(
                    onClick = onStopClicked,
                    enabled = currentlyPlayingAssetPath != null,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text("Stop")
                }
            }
        }
    }
}

@Composable
fun ClipLibraryRoute(
    viewModel: ClipLibraryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ClipLibraryScreen(
        clips = uiState.clips,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        currentlyPlayingAssetPath = uiState.currentlyPlayingAssetPath,
        onClipClicked = viewModel::onClipClicked,
        onStopClicked = viewModel::onStopClicked,
        onViewModeChanged = viewModel::onViewModeChanged,
        selectedMode = uiState.selectedMode
    )
}

@Composable
fun ClipListItem(
    clip: VoiceClip,
    isPlaying: Boolean,
    onClipClicked: (VoiceClip) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { onClipClicked(clip) },
            modifier = Modifier.fillMaxWidth(0.70f)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // title centered
                Text(clip.title)

                // icon to the left
                if (isPlaying) {
                    Icon(
                        imageVector = Icons.Filled.GraphicEq,
                        contentDescription = "Playing...",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(18.dp)
                    )
                }
            }
        }
    }
}