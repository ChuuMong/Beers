package io.chuuhomg.beers.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.widget.Toast
import io.chuuhomg.beers.R
import io.chuuhomg.beers.data.remote.model.Beer
import io.chuuhomg.beers.util.loadUrl
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_beer_buy.*
import java.lang.IllegalArgumentException
import java.text.NumberFormat
import java.util.*

class BeerBuyActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BEER = "EXTRA_BEER"

        private const val MIN_BEER_BUY_QUANTITY = 1
        private const val MAX_BEER_BUY_QUANTITY = 999
    }

    private val quantityPriceSubject = PublishSubject.create<Boolean>()
    private var quantity = MIN_BEER_BUY_QUANTITY

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_buy)

        etUserContract.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val beer = intent.getParcelableExtra<Beer>(EXTRA_BEER) ?: throw IllegalArgumentException()

        tvBeerName.text = beer.name
        tvBeerPrice.text = getCurrencyPrice(beer.price)
        ivBeer.loadUrl(beer.image)

        setQuantityView(beer.price)

        quantityPriceSubject.map {
            if (it) {
                ++quantity
            } else {
                --quantity
            }
        }.map { it * beer.price }.subscribe { setQuantityView(it) }

        btnQuantityDecrease.setOnClickListener {
            if (checkMinQuantity()) {
                Toast.makeText(this, R.string.buy_min_quantity, Toast.LENGTH_SHORT).show()
            } else {
                quantityPriceSubject.onNext(false)
            }
        }

        btnQuantityIncrease.setOnClickListener {
            if (checkMaxQuantity()) {
                Toast.makeText(this, R.string.buy_max_quantity, Toast.LENGTH_SHORT).show()
            } else {
                quantityPriceSubject.onNext(true)
            }
        }

        btnBuy.setOnClickListener {
            if (checkInputUserInfo()) {
                return@setOnClickListener
            }

            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun checkInputUserInfo(): Boolean {
        val userName = etUserName.text.toString()
        val userAddress = etUserAddress.text.toString()
        val userContract = etUserContract.text.toString()

        if (userName.isEmpty()) {
            Toast.makeText(this, R.string.need_input_user_name, Toast.LENGTH_SHORT).show()
            return true
        }

        if (userAddress.isEmpty()) {
            Toast.makeText(this, R.string.need_input_user_address, Toast.LENGTH_SHORT).show()
            return true
        }

        if (userContract.isEmpty()) {
            Toast.makeText(this, R.string.need_input_user_contract, Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }

    private fun getCurrencyPrice(price: Double) = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(price)

    private fun setQuantityView(price: Double) {
        tvBuyQuantity.text = quantity.toString()
        tvTotalPrice.text = getCurrencyPrice(price)
    }

    private fun checkMinQuantity(): Boolean {
        return quantity <= MIN_BEER_BUY_QUANTITY
    }

    private fun checkMaxQuantity(): Boolean {
        return quantity >= MAX_BEER_BUY_QUANTITY
    }
}
