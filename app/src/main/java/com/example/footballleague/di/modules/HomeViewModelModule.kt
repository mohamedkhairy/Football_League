package com.example.footballleague.di.modules


import androidx.lifecycle.ViewModel
import com.example.footballleague.di.key.ViewModelKey
import com.example.footballleague.fragment.home.HomeViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHragmentViewModel(viewModel: HomeViewModel): ViewModel


}
