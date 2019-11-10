package io.chuuhomg.beers

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.chuuhomg.beers.di.DaggerAppComponent
import io.realm.Realm

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}