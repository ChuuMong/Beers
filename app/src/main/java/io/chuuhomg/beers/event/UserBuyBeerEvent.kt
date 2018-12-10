package io.chuuhomg.beers.event

import io.chuuhomg.beers.data.model.Beer
import io.chuuhomg.beers.data.model.User

data class UserBuyBeerEvent(val user: User, val beer: Beer)