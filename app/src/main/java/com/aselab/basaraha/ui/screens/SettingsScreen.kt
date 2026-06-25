package com.aselab.basaraha.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aselab.basaraha.ui.components.AppTopBar
import com.aselab.basaraha.ui.components.InfoCard
import com.aselab.basaraha.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val darkMode by viewModel.darkMode.collectAsState()
    val soundEnabled by viewModel.soundEnabled.collectAsState()
    val vibrationEnabled by viewModel.vibrationEnabled.collectAsState()
    val resetDone by viewModel.resetDone.collectAsState()

    var showResetDialog by remember { mutableStateOf(false) }

    if (resetDone) {
        AlertDialog(
            onDismissRequest = { viewModel.clearResetDone() },
            title = { Text("تمت إعادة التعيين") },
            text = { Text("تمت إعادة تعيين جميع البيانات واستعادة الأسئلة الافتراضية.") },
            confirmButton = {
                TextButton(onClick = { viewModel.clearResetDone() }) {
                    Text("حسناً")
                }
            }
        )
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("إعادة تعيين البيانات") },
            text = { Text("سيتم حذف جميع الأسئلة المخصصة واستعادة الأسئلة الافتراضية. هل تريد المتابعة؟") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetAllData()
                    showResetDialog = false
                }) {
                    Text("نعم، إعادة التعيين")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("إلغاء")
                }
            }
        )
    }

    Scaffold(
        topBar = { AppTopBar(title = "الإعدادات", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SettingItem(
                title = "الوضع الليلي",
                subtitle = "تفعيل المظهر الداكن",
                checked = darkMode,
                onCheckedChange = viewModel::setDarkMode
            )
            SettingItem(
                title = "الأصوات",
                subtitle = "تشغيل صوت عند الانتقال",
                checked = soundEnabled,
                onCheckedChange = viewModel::setSoundEnabled
            )
            SettingItem(
                title = "الاهتزاز",
                subtitle = "اهتزاز عند الانتقال",
                checked = vibrationEnabled,
                onCheckedChange = viewModel::setVibrationEnabled
            )

            InfoCard(
                title = "إعادة تعيين البيانات",
                content = "اضغط على الزر أدناه لحذف الأسئلة المخصصة واستعادة الأسئلة الافتراضية.",
                modifier = Modifier.padding(top = 24.dp)
            )

            TextButton(
                onClick = { showResetDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "إعادة تعيين جميع البيانات",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun SettingItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
