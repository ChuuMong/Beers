package io.chuuhomg.beers.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import io.chuuhomg.beers.R
import io.chuuhomg.beers.data.remote.model.Beer
import io.chuuhomg.beers.presenter.main.MainPresenter
import io.chuuhomg.beers.presenter.main.MainView
import io.chuuhomg.beers.ui.adapter.BeersAdapter
import io.chuuhomg.beers.ui.weiget.InfiniteScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var beersAdapter: BeersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listBeers.layoutManager = LinearLayoutManager(this)

        beersAdapter = BeersAdapter()
        listBeers.adapter = beersAdapter
        listBeers.addOnScrollListener(object : InfiniteScrollListener() {
            override fun onLoadMore() {
                presenter.getBeers()
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            beersAdapter.clear()
            presenter.initGetBeers()
        }

        presenter.attachView(this)
        presenter.getBeers()
    }

    override fun resultBeers(beers: List<Beer>) {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }

        beersAdapter.addAll(beers)
    }

    override fun showErrorMessage(throwable: Throwable) {
        Log.e(TAG, throwable.message, throwable)
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.detachView()

        super.onDestroy()
    }
}
