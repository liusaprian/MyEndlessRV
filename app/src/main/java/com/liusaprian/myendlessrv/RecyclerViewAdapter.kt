package com.liusaprian.myendlessrv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerViewAdapter(private val wordsList: ArrayList<String?>?) : RecyclerView.Adapter<ViewHolder>() {

    private val isWord = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == isWord) {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_words, parent, false)
            WordViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_load, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (viewHolder is WordViewHolder)
            populateList(viewHolder, position)
    }

    override fun getItemCount(): Int {
        return wordsList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        val isLoad = 1
        return if (wordsList?.get(position) == null) isLoad else isWord
    }

    private class WordViewHolder(itemView: View) : ViewHolder(itemView) {
        var tvItem: TextView = itemView.findViewById(R.id.word)
    }

    private class LoadingViewHolder(itemView: View) : ViewHolder(itemView)

    private fun populateList(viewHolder: WordViewHolder, position: Int) {
        val item = wordsList?.get(position)
        viewHolder.tvItem.text = item
    }
}