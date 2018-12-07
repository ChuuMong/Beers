package io.chuuhomg.beers.ui.weiget

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class InfiniteScrollListener : RecyclerView.OnScrollListener() {

    companion object {
        private const val VISIBLE_THRESHOLD = 3
    }

    private var layoutManager: LinearLayoutManager? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy < 0) {
            return
        }

        if (layoutManager == null) {
            layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        }

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = layoutManager!!.itemCount
        val firstVisibleItem = layoutManager!!.findFirstVisibleItemPosition()


        if (totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            onLoadMore()
        }
    }

    abstract fun onLoadMore()

}