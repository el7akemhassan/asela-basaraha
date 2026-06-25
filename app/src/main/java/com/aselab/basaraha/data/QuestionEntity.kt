package com.aselab.basaraha.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class QuestionCategory(val labelAr: String) {
    PERSONAL("شخصية"),
    EMBARRASSING("محرجة"),
    DEEP("عميقة"),
    LOVE("حب"),
    FRIENDSHIP("صداقة"),
    SITUATIONS("مواقف"),
    SECRETS("أسرار"),
    FUNNY("مضحكة"),
    CUSTOM("مخصصة")
}

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val category: QuestionCategory = QuestionCategory.CUSTOM,
    val isDefault: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
