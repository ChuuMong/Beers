package io.chuuhomg.beers.presenter.main

import io.chuuhomg.beers.data.repository.BeerRepository
import io.chuuhomg.beers.presenter.Presenter
import javax.inject.Inject

class MainPresenter @Inject
constructor(private val repository: BeerRepository) : Presenter<MainView>() {

    companion object {
        private const val GET_PAGE_COUNT = 20
    }

    fun getBeers(page: Int) {
        view?.showProgress()

        disposable.add(repository.getBeers(page, GET_PAGE_COUNT).doFinally {
            view?.hideProgress()
        }.subscribe({
            view?.resultBeers(it)
        }, {
            view?.showErrorMessage(it)
        }
        ))
    }

}