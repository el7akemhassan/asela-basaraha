package com.aselab.basaraha

import android.app.Application
import com.aselab.basaraha.data.AppDatabase
import com.aselab.basaraha.data.QuestionRepository
import com.aselab.basaraha.data.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AselaBasarahaApp : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database by lazy { AppDatabase.getInstance(this) }
    val questionRepository by lazy { QuestionRepository(database.questionDao()) }
    val settingsRepository by lazy { SettingsRepository(this) }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            questionRepository.seedDefaultQuestionsIfNeeded()
        }
    }
}
