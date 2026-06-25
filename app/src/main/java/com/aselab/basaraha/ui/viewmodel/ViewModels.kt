package com.aselab.basaraha.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aselab.basaraha.data.QuestionCategory
import com.aselab.basaraha.data.QuestionEntity
import com.aselab.basaraha.data.QuestionRepository
import com.aselab.basaraha.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuestionsViewModel(
    private val repository: QuestionRepository
) : ViewModel() {

    val questions = repository.allQuestions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun addQuestion(text: String, category: QuestionCategory = QuestionCategory.CUSTOM) {
        if (text.isBlank()) {
            _message.value = "يرجى كتابة السؤال"
            return
        }
        viewModelScope.launch {
            repository.insert(text, category)
            _message.value = "تم حفظ السؤال"
        }
    }

    fun updateQuestion(question: QuestionEntity, newText: String) {
        if (newText.isBlank()) {
            _message.value = "يرجى كتابة السؤال"
            return
        }
        viewModelScope.launch {
            repository.update(question.copy(text = newText.trim()))
            _message.value = "تم تحديث السؤال"
        }
    }

    fun deleteQuestion(question: QuestionEntity) {
        viewModelScope.launch {
            repository.delete(question)
            _message.value = "تم حذف السؤال"
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}

class QuestionsViewModelFactory(
    private val repository: QuestionRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionsViewModel(repository) as T
    }
}

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val questionRepository: QuestionRepository
) : ViewModel() {

    val darkMode = settingsRepository.darkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )
    val soundEnabled = settingsRepository.soundEnabled.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )
    val vibrationEnabled = settingsRepository.vibrationEnabled.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    private val _resetDone = MutableStateFlow(false)
    val resetDone: StateFlow<Boolean> = _resetDone.asStateFlow()

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setDarkMode(enabled) }
    }

    fun setSoundEnabled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setSoundEnabled(enabled) }
    }

    fun setVibrationEnabled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setVibrationEnabled(enabled) }
    }

    fun resetAllData() {
        viewModelScope.launch {
            questionRepository.resetAllData()
            _resetDone.value = true
        }
    }

    fun clearResetDone() {
        _resetDone.value = false
    }
}

class SettingsViewModelFactory(
    private val settingsRepository: SettingsRepository,
    private val questionRepository: QuestionRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(settingsRepository, questionRepository) as T
    }
}

class GameViewModel(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _players = MutableStateFlow<List<String>>(emptyList())
    val players: StateFlow<List<String>> = _players.asStateFlow()

    private val _currentPlayer = MutableStateFlow("")
    val currentPlayer: StateFlow<String> = _currentPlayer.asStateFlow()

    private val _currentQuestion = MutableStateFlow("")
    val currentQuestion: StateFlow<String> = _currentQuestion.asStateFlow()

    private val _round = MutableStateFlow(0)
    val round: StateFlow<Int> = _round.asStateFlow()

    private val _finished = MutableStateFlow(false)
    val finished: StateFlow<Boolean> = _finished.asStateFlow()

    private var allQuestions: List<QuestionEntity> = emptyList()
    private var remainingQuestions: MutableList<QuestionEntity> = mutableListOf()
    private var remainingPlayers: MutableList<String> = mutableListOf()

    fun setupPlayers(names: List<String>) {
        _players.value = names
        remainingPlayers = names.toMutableList()
        _round.value = 0
        _finished.value = false
        _currentPlayer.value = ""
        _currentQuestion.value = ""
    }

    fun startGame() {
        viewModelScope.launch {
            allQuestions = repository.getAllQuestions()
            remainingQuestions = allQuestions.shuffled().toMutableList()
            remainingPlayers = _players.value.shuffled().toMutableList()
            _round.value = 0
            _finished.value = false
            nextRound()
        }
    }

    fun nextRound() {
        if (remainingQuestions.isEmpty()) {
            _finished.value = true
            _currentPlayer.value = ""
            _currentQuestion.value = "انتهت جميع الأسئلة! شكراً للعب"
            return
        }

        if (remainingPlayers.isEmpty()) {
            remainingPlayers = _players.value.shuffled().toMutableList()
        }

        val player = remainingPlayers.removeAt(0)
        val question = remainingQuestions.removeAt(0)

        _currentPlayer.value = player
        _currentQuestion.value = question.text
        _round.value = _round.value + 1
    }

    fun resetGame() {
        _players.value = emptyList()
        _currentPlayer.value = ""
        _currentQuestion.value = ""
        _round.value = 0
        _finished.value = false
        remainingQuestions.clear()
        remainingPlayers.clear()
    }
}

class GameViewModelFactory(
    private val repository: QuestionRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(repository) as T
    }
}
