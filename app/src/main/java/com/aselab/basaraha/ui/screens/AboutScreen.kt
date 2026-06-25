package com.aselab.basaraha.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aselab.basaraha.ui.components.AppTopBar
import com.aselab.basaraha.ui.components.InfoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = { AppTopBar(title = "حول اللعبة", onBack = onBack) }
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
                text = "أسئلة بصراحة",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "لعبة جماعية تُلعب بين الأصدقاء في نفس المكان. لا تحتاج إلى إنترنت أو تسجيل دخول.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start
            )

            InfoCard(
                title = "طريقة اللعب",
                content = "1. اختر عدد اللاعبين (من 2 إلى 20)\n" +
                    "2. اكتب اسم كل لاعب\n" +
                    "3. اضغط ابدأ\n" +
                    "4. في كل جولة يختار التطبيق لاعباً عشوائياً وسؤالاً عشوائياً\n" +
                    "5. يجيب اللاعب أمام الجميع\n" +
                    "6. اضغط التالي للانتقال للجولة التالية"
            )

            InfoCard(
                title = "قواعد مهمة",
                content = "• لا يتكرر نفس السؤال إلا بعد استهلاك جميع الأسئلة\n" +
                    "• يمكنك إضافة أسئلتك الخاصة وحفظها محلياً\n" +
                    "• التطبيق يعمل بالكامل بدون إنترنت"
            )

            InfoCard(
                title = "أنواع الأسئلة",
                content = "أسئلة شخصية • محرجة • عميقة • عن الحب • عن الصداقة • عن المواقف • عن الأسرار • مضحكة"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "الإصدار 1.0.0",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
