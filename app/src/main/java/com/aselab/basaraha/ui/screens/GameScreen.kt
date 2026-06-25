package com.aselab.basaraha.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aselab.basaraha.ui.components.AppTopBar
import com.aselab.basaraha.ui.components.StatRow
import com.aselab.basaraha.ui.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel,
    soundEnabled: Boolean,
    vibrationEnabled: Boolean,
    onFeedback: () -> Unit,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val currentPlayer by viewModel.currentPlayer.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val round by viewModel.round.collectAsState()
    val finished by viewModel.finished.collectAsState()
    val players by viewModel.players.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "الجولة $round",
                onBack = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            StatRow(label = "عدد اللاعبين", value = "${players.size}")

            AnimatedContent(
                targetState = currentPlayer,
                transitionSpec = {
                    (fadeIn(tween(400)) + scaleIn(tween(400)))
                        .togetherWith(fadeOut(tween(300)))
                },
                label = "player"
            ) { player ->
                Text(
                    text = player.ifEmpty { "..." },
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                AnimatedContent(
                    targetState = currentQuestion,
                    transitionSpec = {
                        fadeIn(tween(500)).togetherWith(fadeOut(tween(300)))
                    },
                    label = "question"
                ) { question ->
                    Text(
                        text = question.ifEmpty { "جاري تحميل السؤال..." },
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (finished) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {
                            viewModel.startGame()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("إعادة اللعب")
                    }
                    Button(
                        onClick = {
                            viewModel.resetGame()
                            onFinish()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("العودة للرئيسية")
                    }
                }
            } else {
                Button(
                    onClick = {
                        if (soundEnabled || vibrationEnabled) onFeedback()
                        viewModel.nextRound()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("التالي")
                }
            }
        }
    }
}
