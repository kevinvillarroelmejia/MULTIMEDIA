package com.example.roomwithaview.ui

import android.app.Application
import com.example.roomwithaview.database.WordRoomDatabase
import com.example.roomwithaview.repository.WordRepository

class WordsApplication : Application() {

    val dataBase by lazy {
        WordRoomDatabase.getDatabase(this)
    }
    val repository: WordRepository by lazy{
        WordRepository(dataBase.wordDao())
    }
}