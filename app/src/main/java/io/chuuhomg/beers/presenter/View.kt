package io.chuuhomg.beers.presenter


interface View {
    fun showErrorMessage(throwable: Throwable)
}