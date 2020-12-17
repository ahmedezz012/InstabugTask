package com.instabug.task.data.locale

import android.content.Context
import android.database.Cursor
import com.instabug.task.data.model.WordsCount
import com.instabug.task.utils.Constants.DataBase.COLUMN_COUNT_INDEX
import com.instabug.task.utils.Constants.DataBase.COLUMN_WORD_INDEX
import com.instabug.task.utils.helpers.SQLiteHelper

class LocalDataSource(var sqliteHelper: SQLiteHelper) : ILocaleDataSource {
    override fun selectAll(): ArrayList<WordsCount>? {
        val arrayList = arrayListOf<WordsCount>()
        val cursor: Cursor? = sqliteHelper.selectAll()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val word: String = cursor.getString(COLUMN_WORD_INDEX)
                    val count: Int = cursor.getInt(COLUMN_COUNT_INDEX)
                    arrayList.add(WordsCount(word, count))
                } while (cursor.moveToNext());
            }
            if (!cursor.isClosed) {
                cursor.close();
            }
        }
        return arrayList
    }

    override fun deleteAll() {
        sqliteHelper.deleteAll()
    }

    override fun insertAll(wordsCount: ArrayList<WordsCount>) {
        sqliteHelper.insertAll(wordsCount)
        sqliteHelper.close()
    }

    override fun update(wordsCount: ArrayList<WordsCount>) {
        deleteAll()
        insertAll(wordsCount)
    }


}