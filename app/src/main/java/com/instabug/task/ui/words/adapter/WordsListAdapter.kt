package com.instabug.task.ui.words.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.instabug.task.R
import com.instabug.task.data.model.WordsCount

class WordsListAdapter(
    private val context: Context,
    val data: ArrayList<WordsCount>
) :
    RecyclerView.Adapter<WordsListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsListViewHolder {
        return WordsListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_words_count,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WordsListViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}