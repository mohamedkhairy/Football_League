package com.example.footballleague.di.modules

import com.example.footballleague.fragment.team.TeamInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TeamInfoFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributesSubscriptionFragment(): TeamInfoFragment
}