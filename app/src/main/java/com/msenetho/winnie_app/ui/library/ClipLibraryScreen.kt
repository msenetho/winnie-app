package com.msenetho.winnie_app.ui.library

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    onFavouriteClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // title
        Text(
            text = "Quotes",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
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
                ),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
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
                ),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
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
                                        onClipClicked = onClipClicked,
                                        onFavouriteClicked = onFavouriteClicked
                                    )
                                }
                            }
                        }

                        ViewMode.GRID -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    start = 16.dp,
                                    top = 16.dp,
                                    end = 16.dp,
                                    bottom = 96.dp
                                ),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(uiState.clips) { clip ->
                                    ClipGridItem(
                                        clip = clip,
                                        onClipClicked = onClipClicked,
                                        onFavouriteClicked = onFavouriteClicked
                                    )
                                }
                            }
                        }
                    }

                    // button for stopping audio
                    if (uiState.clips.any { it.isPlaying }) {
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
    viewModel: ClipLibraryViewModel = viewModel(factory = ClipLibraryViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    ClipLibraryScreen(
        uiState = uiState,
        onClipClicked = viewModel::onClipClicked,
        onStopClicked = viewModel::onStopClicked,
        onViewModeChanged = viewModel::onViewModeChanged,
        onFavouriteClicked = viewModel::onFavouriteClicked
    )
}

@Composable
fun ClipListItem(
    clip: ClipUiModel,
    onClipClicked: (VoiceClip) -> Unit,
    onFavouriteClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { onClipClicked(clip.clip) },
            modifier = Modifier.fillMaxWidth(0.90f)
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
                    if (clip.isPlaying) {
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
                    text = clip.clip.title,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // spacer to keep centered
                Spacer(modifier = Modifier.width(24.dp))
            }
        }

        // favourite icon/button
        IconButton(
            onClick = { onFavouriteClicked(clip.clip.id) },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
        ) {
            Icon(
                imageVector = if (clip.isFavourite) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = if (clip.isFavourite) "Remove from favourites" else "Add to favourites"
            )
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun ClipGridItem (
    clip: ClipUiModel,
    onClipClicked: (VoiceClip) -> Unit,
    onFavouriteClicked: (Int) -> Unit,
) {
    Box {
        Card(
            onClick = { onClipClicked(clip.clip) },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // play icon
                if (clip.isPlaying) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Playing",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                            .size(18.dp)
                    )
                }

                // title
                Text(
                    text = clip.clip.title,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 12.dp), // change if needed
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // favourite icon/button
        IconButton(
            onClick = { onFavouriteClicked(clip.clip.id) },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = if (clip.isFavourite) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = if (clip.isFavourite) "Remove from favourites" else "Add to favourites"
            )
        }
    }
}