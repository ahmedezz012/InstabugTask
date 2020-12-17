package com.instabug.task.data.local

import com.instabug.task.data.model.WordsCount

interface ILocalDataSource {

    fun selectAll(): ArrayList<WordsCount>?
    fun deleteAll(): Int
    fun insertAll(wordsCount: ArrayList<WordsCount>): ArrayList<Long>
    fun update(wordsCount: ArrayList<WordsCount>)
}