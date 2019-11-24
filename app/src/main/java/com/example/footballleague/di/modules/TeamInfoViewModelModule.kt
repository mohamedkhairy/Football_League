package com.example.footballleague.di.modules

import androidx.lifecycle.ViewModel
import com.example.footballleague.di.key.ViewModelKey
import com.example.footballleague.fragment.team.TeamInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TeamInfoViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TeamInfoViewModel::class)
    abstract fun bindTeamFragmentViewModel(viewModel: TeamInfoViewModel): ViewModel

}