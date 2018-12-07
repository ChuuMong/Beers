package io.chuuhomg.beers.data.remote.api

import io.chuuhomg.beers.data.remote.model.Beer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("beers")
    fun getBeers(@Query("page") page: Int, @Query("per_page") perPage: Int): Single<List<Beer>>
}