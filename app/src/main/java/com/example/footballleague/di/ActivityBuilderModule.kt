package com.example.footballleague.di

import com.example.footballleague.MainActivity
import com.example.footballleague.di.modules.HomeFragmentModule
import com.example.footballleague.di.modules.HomeViewModelModule
import com.example.footballleague.di.modules.TeamInfoFragmentModule
import com.example.footballleague.di.modules.TeamInfoViewModelModule
import com.example.footballleague.fragment.team.TeamInfoFragment
import com.example.footballleague.fragment.team.TeamInfoViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class,
        HomeViewModelModule::class,
        TeamInfoFragmentModule::class,
        TeamInfoViewModelModule::class])

    internal abstract fun contributesMainActivity(): MainActivity
}