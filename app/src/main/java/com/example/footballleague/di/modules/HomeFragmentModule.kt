package com.example.footballleague.di.modules


import com.example.footballleague.fragment.home.HomeFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributesSubscriptionFragment(): HomeFragment
}
