package com.liusaprian.myendlessrv

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    var wordsList = ArrayList<String?>()
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        populateRecyclerView()
        showRecyclerList()
        initScrollListener()
    }

    private fun populateRecyclerView() {
        for (i in 1..10) wordsList.add("Word $i")
    }

    private fun showRecyclerList() {
        adapter = RecyclerViewAdapter(wordsList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter
    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == wordsList.size - 1) {
                        loadMore()
                        isLoading = true
                    }
                }
            }

        })
    }

    private fun loadMore() {
        wordsList.add(null)
        adapter.notifyItemInserted(wordsList.size - 1)
        Handler().postDelayed({
            val loadingViewPosition = wordsList.size - 1
            wordsList.removeAt(loadingViewPosition)
            val listCurrentSizeAfterLoadRemoved = wordsList.size
            adapter.notifyItemRemoved(listCurrentSizeAfterLoadRemoved)
            var positionForNewItemRow = listCurrentSizeAfterLoadRemoved + 1
            val nextListSize = positionForNewItemRow + 10
            while (positionForNewItemRow < nextListSize) {
                wordsList.add("Word $positionForNewItemRow")
                positionForNewItemRow++
            }
            adapter.notifyDataSetChanged()
            isLoading = false
        }, 2000)
    }
}