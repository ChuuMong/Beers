package io.chuuhomg.beers.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.chuuhomg.beers.R
import io.chuuhomg.beers.data.remote.model.Beer
import io.chuuhomg.beers.util.loadUrl
import kotlinx.android.synthetic.main.activity_beer_detail.*
import java.lang.IllegalArgumentException

class BeerDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BEER = "EXTRA_BEER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_detail)

        val beer = intent.getParcelableExtra<Beer>(EXTRA_BEER) ?: throw IllegalArgumentException()

        tvName.text = beer.name
        ivBeer.loadUrl(beer.image)

        tvTagLine.text = getString(R.string.double_quote_string, beer.tagline)
        tvDescription.text = beer.description

        tvAbv.text = beer.abv.toString()
        tvIbu.text = beer.ibu.toString()
        tvSrm.text = beer.srm.toString()

        tvFoodPairing.text = beer.foodPairing.joinToString()
        tvBrewersTips.text = beer.brewersTips

        btnBuy.setOnClickListener {

        }
    }
}
