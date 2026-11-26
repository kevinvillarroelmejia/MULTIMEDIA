package com.example.roomwithaview.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.example.roomwithaview.domain.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDAO {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}