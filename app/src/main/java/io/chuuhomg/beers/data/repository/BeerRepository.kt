package io.chuuhomg.beers.data.repository

import io.chuuhomg.beers.data.local.RealmService
import io.chuuhomg.beers.data.remote.api.ApiService
import io.chuuhomg.beers.data.remote.model.Beer
import io.chuuhomg.beers.data.remote.model.BeerViewType
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList
import javax.inject.Inject
import kotlin.random.Random

class BeerRepository @Inject
constructor(
    private val apiService: ApiService,
    private val realmService: RealmService
) {

    fun getBeers(page: Int, perPage: Int): Single<List<Beer>> {
        return apiService.getBeers(page, perPage).map {
            it.forEach { item ->
                val type = Random.nextInt(0, 3)
                when (type) {
                    0 -> item.viewType = BeerViewType.VIEW_TYPE_01
                    1 -> item.viewType = BeerViewType.VIEW_TYPE_02
                    2 -> item.viewType = BeerViewType.VIEW_TYPE_03
                }

                val price = Random.nextDouble(5.toDouble(), 20.toDouble())
                item.price = price
            }
            it
        }.doOnSuccess { beers ->
            val saveBeers = mutableListOf<io.chuuhomg.beers.data.local.model.Beer>()
            beers.forEach {
                val foodPairing = RealmList<String>()
                it.foodPairing.forEach { food ->
                    foodPairing.add(food)
                }

                saveBeers.add(
                    io.chuuhomg.beers.data.local.model.Beer(
                        it.id,
                        it.name,
                        it.tagline,
                        it.description,
                        it.image,
                        it.price,
                        it.abv,
                        it.ibu,
                        it.srm,
                        foodPairing,
                        it.brewersTips
                    )
                )
            }

            realmService.saveBeers(saveBeers)
        }
    }
    
    fun getBeer(id: Int): Flowable<io.chuuhomg.beers.data.local.model.Beer> {
        return realmService.getBeer(id).filter { it.isLoaded }
    }
}