package com.instabug.task.data.local

import android.database.Cursor
import com.instabug.task.data.model.WordsCount
import com.instabug.task.utils.Constants.DataBase.COLUMN_COUNT_INDEX
import com.instabug.task.utils.Constants.DataBase.COLUMN_WORD_INDEX
import com.instabug.task.utils.helpers.SQLiteHelper

class LocalDataSource(var sqliteHelper: SQLiteHelper) : ILocalDataSource {
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

    override fun deleteAll(): Int {
       return sqliteHelper.deleteAll()
    }

    override fun insertAll(wordsCount: ArrayList<WordsCount>): ArrayList<Long> {
        val listOfIds = sqliteHelper.insertAll(wordsCount)
        sqliteHelper.close()
        return listOfIds
    }

    override fun update(wordsCount: ArrayList<WordsCount>) {
        deleteAll()
        insertAll(wordsCount)
    }


}