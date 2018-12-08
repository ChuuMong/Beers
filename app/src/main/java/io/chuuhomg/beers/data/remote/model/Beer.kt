package io.chuuhomg.beers.data.remote.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

enum class BeerViewType {
    VIEW_TYPE_01,
    VIEW_TYPE_02,
    VIEW_TYPE_03
}

@Parcelize
data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    @SerializedName("image_url")
    val image: String,
    /**
     * 맥주의 도수
     */
    val abv: Float,
    /**
     * 맥주의 쓴맛 정도
     */
    val ibu: Float,
    /**
     * 맥주의 색깔 지수
     */
    val srm: Float,
    @SerializedName("food_pairing")
    val foodPairing: List<String>,
    @SerializedName("brewers_tips")
    val brewersTips: String,
    var viewType: BeerViewType
) : Parcelable