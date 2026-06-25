package com.aselab.basaraha.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aselab.basaraha.data.QuestionEntity
import com.aselab.basaraha.ui.components.AppTopBar
import com.aselab.basaraha.ui.viewmodel.QuestionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsScreen(
    viewModel: QuestionsViewModel,
    onBack: () -> Unit
) {
    val questions by viewModel.questions.collectAsState()
    val message by viewModel.message.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var showAddDialog by remember { mutableStateOf(false) }
    var editingQuestion by remember { mutableStateOf<QuestionEntity?>(null) }
    var deletingQuestion by remember { mutableStateOf<QuestionEntity?>(null) }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = { AppTopBar(title = "إدارة الأسئلة", onBack = onBack) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "إضافة سؤال")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "إجمالي الأسئلة: ${questions.size}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(questions, key = { it.id }) { question ->
                    QuestionItem(
                        question = question,
                        onEdit = { editingQuestion = question },
                        onDelete = { deletingQuestion = question }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        QuestionDialog(
            title = "إضافة سؤال جديد",
            initialText = "",
            onDismiss = { showAddDialog = false },
            onConfirm = { text ->
                viewModel.addQuestion(text)
                showAddDialog = false
            }
        )
    }

    editingQuestion?.let { question ->
        QuestionDialog(
            title = "تعديل السؤال",
            initialText = question.text,
            onDismiss = { editingQuestion = null },
            onConfirm = { text ->
                viewModel.updateQuestion(question, text)
                editingQuestion = null
            }
        )
    }

    deletingQuestion?.let { question ->
        AlertDialog(
            onDismissRequest = { deletingQuestion = null },
            title = { Text("حذف السؤال") },
            text = { Text("هل أنت متأكد من حذف هذا السؤال؟") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteQuestion(question)
                    deletingQuestion = null
                }) {
                    Text("حذف")
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingQuestion = null }) {
                    Text("إلغاء")
                }
            }
        )
    }
}

@Composable
private fun QuestionItem(
    question: QuestionEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = question.text,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = question.category.labelAr +
                        if (question.isDefault) " • افتراضي" else " • مخصص",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "تعديل")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "حذف")
            }
        }
    }
}

@Composable
private fun QuestionDialog(
    title: String,
    initialText: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialText) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("السؤال") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(text) }) {
                Text("حفظ")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}
