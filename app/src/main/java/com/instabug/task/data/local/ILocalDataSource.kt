package com.instabug.task.data.local

import com.instabug.task.data.model.WordsCount

interface ILocalDataSource {

    fun selectAll(): ArrayList<WordsCount>?
    fun deleteAll()
    fun insertAll(wordsCount: ArrayList<WordsCount>)
    fun update(wordsCount: ArrayList<WordsCount>)
}