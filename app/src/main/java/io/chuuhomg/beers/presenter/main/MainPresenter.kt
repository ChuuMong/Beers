package io.chuuhomg.beers.presenter.main

import io.chuuhomg.beers.data.remote.model.BeerViewType
import io.chuuhomg.beers.data.repository.BeerRepository
import io.chuuhomg.beers.presenter.Presenter
import javax.inject.Inject
import kotlin.random.Random

class MainPresenter @Inject
constructor(private val repository: BeerRepository) : Presenter<MainView>() {

    companion object {
        private const val GET_PAGE_COUNT = 20
    }

    private var page = 1
    private var isGetBeers = false

    fun getBeers() {
        if (isGetBeers) {
            return
        }

        isGetBeers = true

        disposable.add(repository.getBeers(page, GET_PAGE_COUNT).doFinally {
            isGetBeers = false
        }.filter { !it.isEmpty() }.subscribe({
            view?.resultBeers(it)
            addPage()
        }, {
            view?.showErrorMessage(it)
        }
        ))
    }

    fun initGetBeers() {
        disposable.clear()
        page = 1
        isGetBeers = false

        getBeers()
    }

    private fun addPage() {
        page += 1
    }
}