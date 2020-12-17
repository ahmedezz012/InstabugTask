package com.instabug.task.ui.words

import android.content.Context
import com.instabug.task.data.locale.ILocaleDataSource
import com.instabug.task.data.model.Status
import com.instabug.task.data.model.WordsCount
import com.instabug.task.data.remote.IRemoteDataSource
import com.instabug.task.utils.Utils

class WordsRepository(
    var context: Context,
    var iRemoteDataSource: IRemoteDataSource,
    var iLocalDataSource: ILocaleDataSource
) {

    fun getWordsList(): Status<ArrayList<WordsCount>> {
        return if (Utils.checkConnection(context)) {
            val responseArray = iRemoteDataSource.callInstaBug()
            if (responseArray.isNullOrEmpty()) {
                Status.Error(data = responseArray)
            } else {
                Status.Success(responseArray)
            }
        } else {
            val cachedArray = iLocalDataSource.selectAll()
            if (cachedArray.isNullOrEmpty())
                Status.NoNetwork(data = arrayListOf())
            else
                Status.OfflineData(cachedArray)
        }
    }

    fun cacheWordsList(wordsCount: ArrayList<WordsCount>) {
        iLocalDataSource.update(wordsCount)
    }
}