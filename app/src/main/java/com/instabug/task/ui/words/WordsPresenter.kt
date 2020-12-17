package com.instabug.task.ui.words

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.instabug.task.data.model.Status
import com.instabug.task.data.model.StatusCode
import com.instabug.task.data.model.WordsCount
import java.util.ArrayList

class WordsPresenter(
    var context: Context,
    var wordsView: WordsView,
    var wordsRepository: WordsRepository
) {

    fun getWordsList() {
        wordsView.showHideProgressBar(true)
        Thread(Runnable {
            val resultStatus = wordsRepository.getWordsList()
            (context as AppCompatActivity).runOnUiThread(Runnable {
                wordsView.showHideProgressBar(false)
                handleReceivedStatus(resultStatus)
            })
        }).start()
    }

    private fun handleReceivedStatus(resultStatus: Status<ArrayList<WordsCount>>) {
        when (resultStatus.statusCode) {
            StatusCode.ERROR ->
                wordsView.showError(resultStatus.message)
            StatusCode.NO_NETWORK ->
                wordsView.showError(resultStatus.message)
            else -> {
                if (resultStatus.statusCode == StatusCode.SUCCESS)
                    cacheWordsList(resultStatus.data!!)
                wordsView.onGetWordsSuccess(resultStatus.data!!)
            }
        }
    }

    private fun cacheWordsList(wordsCount: ArrayList<WordsCount>) {

        Thread(Runnable {
            wordsRepository.cacheWordsList(wordsCount)
        }).start()
    }


}