package com.msenetho.winnie_app.ui.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msenetho.winnie_app.domain.model.VoiceClip

@Composable
fun ClipLibraryScreen(
    uiState: ClipLibraryUIState,
    onClipClicked: (VoiceClip) -> Unit,
    onStopClicked: () -> Unit,
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
                .height(40.dp)
        ) {
            SegmentedButton(
                selected = uiState.selectedMode == ViewMode.LIST,
                onClick = { onViewModeChanged(ViewMode.LIST) },
                shape = SegmentedButtonDefaults.itemShape(
                    index = 0,
                    count = 2
                )
            ) {
                // list icon
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "List view"
                )
            }

            SegmentedButton(
                selected = uiState.selectedMode == ViewMode.GRID,
                onClick = { onViewModeChanged(ViewMode.GRID) },
                shape = SegmentedButtonDefaults.itemShape(
                    index = 1,
                    count = 2
                )
            ) {
                // grid icon
                Icon(
                    imageVector = Icons.Filled.GridView,
                    contentDescription = "Grid view"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Text("Loading lines...")
            }

            uiState.errorMessage != null -> {
                Text(uiState.errorMessage)
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (uiState.selectedMode) {
                        ViewMode.LIST -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                contentPadding = PaddingValues(bottom = 96.dp)
                            ) {
                                items(uiState.clips) { clip ->
                                    ClipListItem(
                                        clip = clip,
                                        isPlaying = clip.assetPath == uiState.currentlyPlayingAssetPath,
                                        onClipClicked = onClipClicked,
                                    )
                                }
                            }
                        }

                        ViewMode.GRID -> {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 160.dp),
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(bottom = 96.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(uiState.clips) { clip ->
                                    ClipGridItem(
                                        clip = clip,
                                        isPlaying = clip.assetPath == uiState.currentlyPlayingAssetPath,
                                        onClipClicked = onClipClicked,
                                    )
                                }
                            }
                        }
                    }

                    // button for stopping audio
                    if (uiState.currentlyPlayingAssetPath != null) {
                        FloatingActionButton(
                            onClick = onStopClicked,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CropSquare,
                                contentDescription = "Stop playback"
                            )
                        }
                    }
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
        uiState = uiState,
        onClipClicked = viewModel::onClipClicked,
        onStopClicked = viewModel::onStopClicked,
        onViewModeChanged = viewModel::onViewModeChanged
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // icon to the left
                    if (isPlaying) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Playing...",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                // title centered
                Text(
                    text = clip.title,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // spacer to keep centered
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun ClipGridItem (
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
            modifier = Modifier.fillMaxWidth(0.80f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // icon to the left
                    if (isPlaying) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Playing...",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                // title centered
                Text(
                    text = clip.title,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // spacer to keep centered
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
    }
}