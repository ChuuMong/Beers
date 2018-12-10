package io.chuuhomg.beers.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.chuuhomg.beers.R
import io.chuuhomg.beers.data.remote.model.Beer
import io.chuuhomg.beers.util.loadUrl
import kotlinx.android.synthetic.main.activity_beer_detail.*
import java.lang.IllegalArgumentException
import java.text.NumberFormat
import java.util.*

class BeerDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BEER = "EXTRA_BEER"
        private const val START_BUY_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_detail)

        val beer = intent.getParcelableExtra<Beer>(EXTRA_BEER) ?: throw IllegalArgumentException()

        tvBeerName.text = beer.name
        tvBeerPrice.text = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(beer.price)
        ivBeer.loadUrl(beer.image)

        tvTagLine.text = getString(R.string.double_quote_string, beer.tagline)
        tvDescription.text = beer.description

        tvAbv.text = beer.abv.toString()
        tvIbu.text = beer.ibu.toString()
        tvSrm.text = beer.srm.toString()

        tvFoodPairing.text = beer.foodPairing.joinToString()
        tvBrewersTips.text = beer.brewersTips

        btnBuy.setOnClickListener {
            val intent = Intent(this, BeerBuyActivity::class.java)
            intent.putExtra(BeerBuyActivity.EXTRA_BEER, beer)
            startActivityForResult(intent, START_BUY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_BUY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                finish()
            }
        }
    }
}
