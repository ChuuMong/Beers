package io.chuuhomg.beers.data.remote.model

import com.google.gson.annotations.SerializedName

data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    @SerializedName("first_brewed")
    val firstBrewed: String,
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
    val foodPairings: List<String>,
    @SerializedName("brewers_tips")
    val brewersTips: String,
    @SerializedName("contributed_by")
    val contributedBy: String
)