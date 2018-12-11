package io.chuuhomg.beers.data.local.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith

@Parcelize
open class Beer(
    @PrimaryKey
    @Index
    var id: Int? = 0,
    var name: String? = null,
    var tagline: String? = null,
    var description: String? = null,
    var image: String? = null,
    var price: Double? = .0,
    var abv: Float? = 0f,
    var ibu: Float? = 0f,
    var srm: Float? = 0f,
    var foodPairing: @WriteWith<RealmStringListParceler> RealmList<String>? = null,
    var brewersTips: String? = null
) : RealmObject(), Parcelable

object RealmStringListParceler : Parceler<RealmList<String>?> {
    override fun create(parcel: Parcel): RealmList<String>? {
        return parcel.readRealmStringList()
    }

    override fun RealmList<String>?.write(parcel: Parcel, flags: Int) {
        parcel.writeRealmStringList(this)
    }
}

fun Parcel.readRealmStringList(): RealmList<String>? = when {
    readInt() > 0 -> RealmList<String>().also { list ->
        repeat(readInt()) {
            list.add(readString())
        }
    }
    else -> null
}

fun Parcel.writeRealmStringList(list: RealmList<String>?) {
    writeInt(
        when (list) {
            null -> 0
            else -> 1
        }
    )

    if (list != null) {
        writeInt(list.size)
        list.forEach { writeString(it) }
    }
}