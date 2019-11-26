package com.example.footballleague.di.modules

import com.example.footballleague.fragment.FavoritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoritesFragmentsModule {

    @ContributesAndroidInjector
    internal abstract fun contributesSubscriptionFragment(): FavoritesFragment
}