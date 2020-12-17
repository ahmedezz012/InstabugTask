package com.instabug.task.data.remote

import com.instabug.task.data.model.WordsCount

interface IRemoteDataSource {

    fun callInstaBug():ArrayList<WordsCount>?
}