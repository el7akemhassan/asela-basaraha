package com.aselab.basaraha.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

class QuestionCategoryConverter {
    @TypeConverter
    fun fromCategory(category: QuestionCategory): String = category.name

    @TypeConverter
    fun toCategory(value: String): QuestionCategory =
        QuestionCategory.entries.find { it.name == value } ?: QuestionCategory.CUSTOM
}

@Database(
    entities = [QuestionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(QuestionCategoryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "asela_basaraha.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
