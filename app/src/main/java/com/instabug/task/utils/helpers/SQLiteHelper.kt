package com.instabug.task.utils.helpers


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.instabug.task.data.model.WordsCount
import com.instabug.task.utils.Constants.DataBase.BYTE_ARRAY_SIZE
import com.instabug.task.utils.Constants.DataBase.COLUMN_COUNT
import com.instabug.task.utils.Constants.DataBase.COLUMN_WORD
import com.instabug.task.utils.Constants.DataBase.DB_NAME
import com.instabug.task.utils.Constants.DataBase.DELETE_ALL_QUERY
import com.instabug.task.utils.Constants.DataBase.FULL_DB_PATH
import com.instabug.task.utils.Constants.DataBase.SELECT_ALL_QUERY
import com.instabug.task.utils.Constants.DataBase.WORDS_TABLE_NAME
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


class SQLiteHelper(var context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    private var sqLiteDatabase: SQLiteDatabase? = null

    init {
        createDataBase()
        openDataBase()
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun close() {
        sqLiteDatabase?.close();
        super.close();
    }

    private fun openDataBase() {
        try {
            close()
            sqLiteDatabase =
                SQLiteDatabase.openDatabase(
                    getFullDataBasePath(),
                    null,
                    SQLiteDatabase.OPEN_READWRITE
                )
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    private fun createDataBase() {
        val exist: Boolean = checkIfDataBaseExist()
        if (!exist) {
            this.readableDatabase
            copyDataBase()
        }
    }

    private fun copyDataBase() {
        try {
            val inputStream: InputStream = context.assets.open(DB_NAME)
            val outputStream: OutputStream = FileOutputStream(getFullDataBasePath())
            val byteArray = ByteArray(BYTE_ARRAY_SIZE)
            var len: Int
            while (inputStream.read(byteArray).also { len = it } > 0) {
                outputStream.write(byteArray, 0, len)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (exception: java.lang.Exception) {
            exception.printStackTrace()
        }
    }

    private fun checkIfDataBaseExist(): Boolean {
        var database: SQLiteDatabase? = null
        try {
            database = SQLiteDatabase.openDatabase(
                getFullDataBasePath(),
                null,
                SQLiteDatabase.OPEN_READONLY
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        database?.close()
        return database != null
    }

    private fun getFullDataBasePath(): String {
        return FULL_DB_PATH
    }

    fun insertAll(wordsCount: ArrayList<WordsCount>): ArrayList<Long> {
        val listOfIds = arrayListOf<Long>()
        try {
            sqLiteDatabase?.beginTransaction()
            val values = ContentValues()
            for (item in wordsCount) {
                values.put(COLUMN_WORD, item.word)
                values.put(COLUMN_COUNT, item.count)
                val id: Long? = sqLiteDatabase?.insert(WORDS_TABLE_NAME, null, values)
                id?.let {
                    listOfIds.add(it)
                }
            }
            sqLiteDatabase?.setTransactionSuccessful()
        } finally {
            sqLiteDatabase?.endTransaction()
        }
        return listOfIds
    }

    fun selectAll(): Cursor? {
        return sqLiteDatabase?.rawQuery(
            SELECT_ALL_QUERY,
            null
        );
    }

    fun deleteAll(): Int {
        val numberOfDeletedRows: Int?
        numberOfDeletedRows = try {
            sqLiteDatabase?.delete(WORDS_TABLE_NAME, null, null)
        } catch (exc: java.lang.Exception) {
            exc.printStackTrace()
            0
        }
        return numberOfDeletedRows ?: 0
    }

}