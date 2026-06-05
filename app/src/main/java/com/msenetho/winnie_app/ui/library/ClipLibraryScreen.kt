package com.msenetho.winnie_app.ui.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msenetho.winnie_app.domain.model.VoiceClip

@Composable
fun ClipLibraryScreen(
    clips: List<VoiceClip>,
    isLoading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pooh's Voicelines",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Text("Loading lines...")
            }

            errorMessage != null -> {
                Text(errorMessage)
            }

            else -> {
                clips.forEach { clip ->
                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(0.80f)
                    ) {
                        Text(clip.title)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
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
        errorMessage = uiState.errorMessage
    )
}