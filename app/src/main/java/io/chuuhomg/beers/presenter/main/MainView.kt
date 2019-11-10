package io.chuuhomg.beers.presenter.main

import io.chuuhomg.beers.data.remote.model.Beer
import io.chuuhomg.beers.presenter.View


interface MainView : View {
    fun resultBeers(beers: List<Beer>)
}