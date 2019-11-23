package com.example.footballleague.di

import androidx.lifecycle.ViewModelProvider
import com.example.footballleague.viewmodel.ViewModelProviderFactory

import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}
