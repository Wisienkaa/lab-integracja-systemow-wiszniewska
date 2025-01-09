package com.mojapka.amw_prime.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mojapka.amw_prime.models.Question

@Dao
interface QuestionDao {
    @Insert
    suspend fun addQuestion(question: Question)

    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<Question>
}
