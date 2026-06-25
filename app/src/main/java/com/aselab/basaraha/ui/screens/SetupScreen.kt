package com.aselab.basaraha.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.aselab.basaraha.ui.components.AppTopBar
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen(
    onBack: () -> Unit,
    onStart: (List<String>) -> Unit
) {
    var playerCount by remember { mutableFloatStateOf(4f) }
    val count = playerCount.roundToInt().coerceIn(2, 20)
    val names = remember { mutableStateListOf(*Array(count) { "" }) }

    if (names.size != count) {
        names.clear()
        repeat(count) { names.add("") }
    }

    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { AppTopBar(title = "إعداد اللعبة", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "عدد اللاعبين: $count",
                style = MaterialTheme.typography.titleMedium
            )
            Slider(
                value = playerCount,
                onValueChange = { playerCount = it.coerceIn(2f, 20f) },
                valueRange = 2f..20f,
                steps = 17,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "أسماء اللاعبين",
                style = MaterialTheme.typography.titleMedium
            )

            repeat(count) { index ->
                OutlinedTextField(
                    value = names[index],
                    onValueChange = { names[index] = it },
                    label = { Text("اللاعب ${index + 1}") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val trimmed = names.map { it.trim() }
                    when {
                        trimmed.any { it.isBlank() } -> error = "يرجى إدخال جميع الأسماء"
                        trimmed.distinct().size != trimmed.size -> error = "الأسماء يجب أن تكون مختلفة"
                        else -> {
                            error = null
                            onStart(trimmed)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ابدأ")
            }
        }
    }
}
