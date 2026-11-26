package com.example.roomwithaview.repository

import androidx.annotation.WorkerThread
import com.example.roomwithaview.database.WordDAO
import com.example.roomwithaview.domain.Word

class WordRepository(private val wordDAO: WordDAO) {

    val allWords = wordDAO.getAlphabetizedWords()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDAO.insert(word)
    }
}