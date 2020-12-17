package com.instabug.task.utils.helpers

import android.content.Context
import com.instabug.task.data.local.LocalDataSource
import com.instabug.task.data.remote.RemoteDataSource
import com.instabug.task.ui.words.WordsRepository
import com.instabug.task.utils.Constants

object ObjectsProvider {
    fun provideSQLiteHelper(context: Context): SQLiteHelper {
        return SQLiteHelper(context, Constants.DataBase.DB_NAME, Constants.DataBase.DB_VERSION)
    }

    fun provideLocalDataSource(context: Context): LocalDataSource {
        return LocalDataSource(provideSQLiteHelper(context))
    }

    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource()
    }

    fun provideWordsRepository(context: Context): WordsRepository {
        return WordsRepository(context, provideRemoteDataSource(), provideLocalDataSource(context))
    }
}