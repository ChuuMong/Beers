package io.chuuhomg.beers.di.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.chuuhomg.beers.ui.activity.BeerDetailActivity
import io.chuuhomg.beers.ui.activity.MainActivity

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun beerDatailActivity(): BeerDetailActivity
}
