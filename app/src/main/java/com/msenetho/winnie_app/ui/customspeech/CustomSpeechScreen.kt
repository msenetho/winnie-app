package com.msenetho.winnie_app.ui.customspeech

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun CustomSpeechScreen(
    uiState: CustomSpeechUIState,
    onPromptChanged: (String) -> Unit,
    onCreateClicked: () -> Unit,
    onStopClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Custom Voicelines",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.prompt,
            onValueChange = onPromptChanged,
            label = { Text("Enter line") },
            supportingText = {
                Text(
                    text = uiState.errorMessage
                        ?: "${uiState.maxChar - uiState.remainingChar} / ${uiState.maxChar}"
                )
            },
            isError = uiState.errorMessage != null,
            modifier = Modifier.fillMaxWidth(0.80f),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCreateClicked,
            enabled = uiState.canGenerate,
            modifier = Modifier.fillMaxWidth(0.80f)
        ) {
            Text(if (uiState.isGenerating) "Creating..." else "Create")
        }

        Button(
            onClick = onStopClicked,
            enabled = uiState.isPlaying,
            modifier = Modifier.fillMaxWidth(0.80f)
        ) {
            Text("Stop")
        }
    }
}