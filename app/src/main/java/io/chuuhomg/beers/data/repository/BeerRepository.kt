package io.chuuhomg.beers.data.repository

import io.chuuhomg.beers.data.remote.api.ApiService
import io.chuuhomg.beers.data.model.Beer
import io.reactivex.Single
import javax.inject.Inject

class BeerRepository @Inject
constructor(private val apiService: ApiService) {

    fun getBeers(page: Int, perPage: Int): Single<List<Beer>> {
        return apiService.getBeers(page, perPage)
    }
}