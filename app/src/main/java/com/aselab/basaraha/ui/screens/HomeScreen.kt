package com.aselab.basaraha.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aselab.basaraha.ui.components.MenuButton

@Composable
fun HomeScreen(
    onStartGame: () -> Unit,
    onAddQuestions: () -> Unit,
    onSettings: () -> Unit,
    onAbout: () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(800),
        label = "fade"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .alpha(alpha),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "أسئلة بصراحة",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Text(
                text = "لعبة جماعية ممتعة بين الأصدقاء",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
            )

            MenuButton(
                text = "ابدأ اللعبة",
                subtitle = "اختر اللاعبين وابدأ المتعة",
                onClick = onStartGame
            )
            Spacer(modifier = Modifier.height(12.dp))
            MenuButton(
                text = "إضافة أسئلة",
                subtitle = "أضف أسئلتك الخاصة",
                onClick = onAddQuestions
            )
            Spacer(modifier = Modifier.height(12.dp))
            MenuButton(
                text = "الإعدادات",
                subtitle = "الوضع الليلي والأصوات",
                onClick = onSettings
            )
            Spacer(modifier = Modifier.height(12.dp))
            MenuButton(
                text = "حول اللعبة",
                subtitle = "تعرف على طريقة اللعب",
                onClick = onAbout
            )
        }
    }
}
