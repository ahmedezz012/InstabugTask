package com.instabug.task.ui.words.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.instabug.task.data.model.WordsCount
import kotlinx.android.synthetic.main.item_words_count.view.*

class WordsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(item: WordsCount) {
        bindCount(item.count)
        bindWord(item.word)
    }

    private fun bindWord(word: String) {
        itemView.txtWord.text = word
    }

    private fun bindCount(count: Int) {
        itemView.txtCount.text = count.toString()
    }
}