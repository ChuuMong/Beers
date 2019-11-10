package io.chuuhomg.beers.presenter.detail

import io.chuuhomg.beers.data.local.model.Beer
import io.chuuhomg.beers.presenter.View


/**
 * Created by Home on 2018-12-11.
 */
interface DetailView : View {
    fun resultBeer(beer: Beer)
}