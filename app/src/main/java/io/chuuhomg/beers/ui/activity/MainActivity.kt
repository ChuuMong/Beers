package io.chuuhomg.beers.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import io.chuuhomg.beers.R
import io.chuuhomg.beers.data.model.Beer
import io.chuuhomg.beers.event.UserBuyBeerEvent
import io.chuuhomg.beers.presenter.main.MainPresenter
import io.chuuhomg.beers.presenter.main.MainView
import io.chuuhomg.beers.ui.adapter.BeersAdapter
import io.chuuhomg.beers.ui.adapter.OnClickBeerItemListener
import io.chuuhomg.beers.ui.weiget.InfiniteScrollListener
import io.chuuhomg.beers.util.EventBus
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView, OnClickBeerItemListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var beersAdapter: BeersAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listBeers.layoutManager = LinearLayoutManager(this)

        beersAdapter = BeersAdapter(this)
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

        EventBus.register().subscribe {
            if (it is UserBuyBeerEvent) {
                Toast.makeText(this,
                    getString(R.string.result_user_buy_beer, it.user.userName, it.beer.name),
                    Toast.LENGTH_SHORT).show()
            }
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

    override fun onClickBeerItem(tvName: TextView, ivBeer: ImageView, beer: Beer) {
        val intent = Intent(this, BeerDetailActivity::class.java)
        intent.putExtra(BeerDetailActivity.EXTRA_BEER, beer)

        val tvPair = Pair.create(tvName as View, getString(R.string.beer_name))
        val ivPair = Pair.create(ivBeer as View, getString(R.string.beer_image))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, tvPair, ivPair)
        startActivity(intent, options.toBundle())
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
