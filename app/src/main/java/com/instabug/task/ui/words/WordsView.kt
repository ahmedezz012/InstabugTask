package com.instabug.task.ui.words

import androidx.annotation.StringRes
import com.instabug.task.data.model.WordsCount

interface WordsView {
    fun onGetWordsSuccess(wordsCount: ArrayList<WordsCount>)
    fun showHideProgressBar(show: Boolean)
    fun showError(@StringRes errorMessage: Int)
}