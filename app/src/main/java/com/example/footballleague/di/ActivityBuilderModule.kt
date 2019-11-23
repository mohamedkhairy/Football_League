package com.example.footballleague.di

import com.example.footballleague.MainActivity
import com.example.footballleague.di.modules.HomeFragmentModule
import com.example.footballleague.di.modules.HomeViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class, HomeViewModelModule::class])
    internal abstract fun contributesMainActivity(): MainActivity
}