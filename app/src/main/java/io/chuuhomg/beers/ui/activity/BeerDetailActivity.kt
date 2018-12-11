package io.chuuhomg.beers.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import io.chuuhomg.beers.R
import io.chuuhomg.beers.data.local.model.Beer
import io.chuuhomg.beers.presenter.detail.DetailPresenter
import io.chuuhomg.beers.presenter.detail.DetailView
import io.chuuhomg.beers.util.loadUrl
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_beer_detail.*
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class BeerDetailActivity : DaggerAppCompatActivity(), DetailView {

    companion object {
        const val EXTRA_BEER_ID = "EXTRA_BEER_ID"
        private val TAG = BeerDetailActivity::class.java.simpleName
        private const val START_BUY_REQUEST_CODE = 1001
    }

    @Inject
    lateinit var presenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_detail)

        val id = intent.getIntExtra(EXTRA_BEER_ID, 0)
        if (id == 0) {
            throw IllegalArgumentException()
        }

        presenter.attachView(this)
        presenter.getBeer(id)
    }

    override fun resultBeer(beer: Beer) {
        tvBeerName.text = beer.name
        tvBeerPrice.text = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(beer.price)
        if (!beer.image.isNullOrEmpty()) {
            ivBeer.loadUrl(beer.image!!)
        }
        tvTagLine.text = getString(R.string.double_quote_string, beer.tagline)
        tvDescription.text = beer.description

        tvAbv.text = beer.abv.toString()
        tvIbu.text = beer.ibu.toString()
        tvSrm.text = beer.srm.toString()

        tvFoodPairing.text = beer.foodPairing?.joinToString()
        tvBrewersTips.text = beer.brewersTips

        btnBuy.setOnClickListener {
            val intent = Intent(this, BeerBuyActivity::class.java)
            intent.putExtra(BeerBuyActivity.EXTRA_BEER, beer)
            startActivityForResult(intent, START_BUY_REQUEST_CODE)
        }
    }

    override fun showErrorMessage(throwable: Throwable) {
        Log.e(TAG, throwable.message, throwable)
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_BUY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                finish()
            }
        }
    }
}
