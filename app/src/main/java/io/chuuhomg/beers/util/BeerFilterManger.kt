package io.chuuhomg.beers.util

import io.chuuhomg.beers.data.remote.model.Beer

class BeerFilterManger {

    companion object {
        private const val MAX_PRICE = 21
        private const val MAX_ABV = 21
        private const val MAX_IBU = 121
        private const val MAX_SRM = 101
    }

    private val filterTable = mutableMapOf<BeerFilterType, Int>()

    init {
        filterTable[BeerFilterType.PRICE] = MAX_PRICE
        filterTable[BeerFilterType.ABV] = MAX_ABV
        filterTable[BeerFilterType.IBU] = MAX_IBU
        filterTable[BeerFilterType.SRM] = MAX_SRM
    }

    fun getFilterList(beers: List<Beer>, filterType: Pair<BeerFilterType, Int>): List<Beer> {
        filterTable[filterType.first] = filterType.second

        return getFilterList(beers)
    }

    fun getFilterList(beers: List<Beer>): List<Beer> {
        val filterList = mutableListOf<Beer>()

        filterList.addAll(beers.filter { it.price <= filterTable[BeerFilterType.PRICE]!! }
            .filter { it.abv <= filterTable[BeerFilterType.ABV]!! }
            .filter { it.ibu <= filterTable[BeerFilterType.IBU]!! }
            .filter { it.srm <= filterTable[BeerFilterType.SRM]!! }
        )

        return filterList
    }
}

enum class BeerFilterType {
    PRICE, ABV, IBU, SRM
}