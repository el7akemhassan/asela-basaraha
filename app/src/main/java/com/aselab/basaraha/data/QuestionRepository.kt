package com.aselab.basaraha.data

import kotlinx.coroutines.flow.Flow

class QuestionRepository(private val dao: QuestionDao) {

    val allQuestions: Flow<List<QuestionEntity>> = dao.getAllQuestionsFlow()

    suspend fun getAllQuestions(): List<QuestionEntity> = dao.getAllQuestions()

    suspend fun getCount(): Int = dao.getCount()

    suspend fun insert(text: String, category: QuestionCategory = QuestionCategory.CUSTOM): Long {
        return dao.insert(
            QuestionEntity(
                text = text.trim(),
                category = category,
                isDefault = false
            )
        )
    }

    suspend fun update(question: QuestionEntity) = dao.update(question)

    suspend fun delete(question: QuestionEntity) = dao.delete(question)

    suspend fun seedDefaultQuestionsIfNeeded() {
        if (dao.getCount() == 0) {
            val defaults = DefaultQuestions.all.map { (text, category) ->
                QuestionEntity(
                    text = text,
                    category = category,
                    isDefault = true
                )
            }
            dao.insertAll(defaults)
        }
    }

    suspend fun resetAllData() {
        dao.deleteAll()
        seedDefaultQuestionsIfNeeded()
    }
}
