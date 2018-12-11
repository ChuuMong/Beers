package io.chuuhomg.beers.presenter.detail

import io.chuuhomg.beers.data.repository.BeerRepository
import io.chuuhomg.beers.presenter.Presenter
import javax.inject.Inject

class DetailPresenter @Inject
constructor(private val beerRepository: BeerRepository) : Presenter<DetailView>() {

    fun getBeer(id: Int) {
        disposable.add(beerRepository.getBeer(id).subscribe({
            view?.resultBeer(it)
        }, {
            view?.showErrorMessage(it)
        }))
    }
}