package io.chuuhomg.beers.util

import io.reactivex.subjects.PublishSubject

object EventBus {
    private val bus = PublishSubject.create<Any>()

    fun send(any: Any) {
        bus.onNext(any)
    }

    fun register() = bus

}