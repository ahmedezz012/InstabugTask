package com.instabug.task.utils

import android.net.Network

object Constants {

    object Network {
        const val INSTA_BUG_URL = "https://instabug.com/"
        const val CHAR_SET = "UTF-8"
        const val WORDS_REGEX = "[a-zA-Z]+"
    }


    object DataBase {
        const val DB_NAME = "words.sqlite"
        const val DB_VERSION = 1
        const val BYTE_ARRAY_SIZE = 1024
        const val DB_PATH = "/data/data/com.instabug.task/databases/"
        const val FULL_DB_PATH = DB_PATH + DB_NAME
        const val WORDS_TABLE_NAME = "words_table"
        const val DELETE_ALL_QUERY = "delete from $WORDS_TABLE_NAME"
        const val SELECT_ALL_QUERY = "select * from $WORDS_TABLE_NAME"
        const val COLUMN_WORD = "word"
        const val COLUMN_COUNT = "count"
        const val COLUMN_WORD_INDEX = 0
        const val COLUMN_COUNT_INDEX = 1
    }

}