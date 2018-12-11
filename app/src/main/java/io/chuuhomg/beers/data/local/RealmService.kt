package io.chuuhomg.beers.data.local

import io.chuuhomg.beers.data.local.model.Beer
import io.reactivex.Flowable
import io.realm.Realm
import javax.inject.Inject

class RealmService @Inject constructor(private val realm: Realm) {

    fun saveBeers(beers: List<Beer>) {
        realm.executeTransactionAsync {
            it.copyToRealmOrUpdate(beers)
        }
    }

    fun getBeer(id: Int): Flowable<Beer> {
        return realm.where(Beer::class.java).equalTo("id", id).findFirstAsync().asFlowable<Beer>()
    }
}