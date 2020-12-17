package com.instabug.task.data.locale

import com.instabug.task.data.model.WordsCount

interface ILocaleDataSource {

    fun selectAll(): ArrayList<WordsCount>?
    fun deleteAll()
    fun insertAll(wordsCount: ArrayList<WordsCount>)
    fun update(wordsCount: ArrayList<WordsCount>)
}