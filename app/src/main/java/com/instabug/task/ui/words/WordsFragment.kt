package com.instabug.task.ui.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.instabug.task.R
import com.instabug.task.data.model.WordsCount
import com.instabug.task.ui.words.adapter.WordsListAdapter
import com.instabug.task.utils.helpers.ObjectsProvider.provideWordsRepository
import kotlinx.android.synthetic.main.fragment_main.*


class WordsFragment : Fragment(), WordsView {

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
        fun newInstance(): WordsFragment =
            WordsFragment()
    }

    private lateinit var mWordsPresenter: WordsPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            mWordsPresenter = WordsPresenter(
                it, this,
                provideWordsRepository(it)
            )
            mWordsPresenter.getWordsList()
        }

    }

    override fun onGetWordsSuccess(wordsCount: ArrayList<WordsCount>) {
        rvWords.visibility = View.VISIBLE
        val wordsListAdapter =
            WordsListAdapter(
                context!!,
                wordsCount
            )
        rvWords.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        rvWords.adapter = wordsListAdapter
    }

    override fun showHideProgressBar(show: Boolean) {
        progress.visibility =
            if (show) View.VISIBLE else View.GONE
    }

    override fun showError(errorMessage: Int) {
        txtErrorMessage.visibility = View.VISIBLE
        txtErrorMessage.setText(errorMessage)
    }
}