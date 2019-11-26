package com.example.footballleague.fragment.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.footballleague.database.entity.Favorites
import com.example.footballleague.database.entity.Team
import com.example.footballleague.repository.Repository
import com.example.footballleague.resource.Resource
import kotlinx.coroutines.Job
import javax.inject.Inject

class HomeViewModel
@Inject
constructor(private val userRepository: Repository, val app: Application) : AndroidViewModel(app) {


    private val jobs: MutableList<Job> = mutableListOf()

    private val liveComeBackData: MediatorLiveData<List<Team>> = MediatorLiveData()

    private val liveLoading: MutableLiveData<Boolean> = MutableLiveData()

    private val liveConnect: MutableLiveData<Boolean> = MutableLiveData()


    fun getLoading(): LiveData<Boolean> = liveLoading

    fun getConnection(): LiveData<Boolean> = liveConnect

    fun getTeamsData(): LiveData<List<Team>> = liveComeBackData


    fun getFootballTeams() {


        val teamData = userRepository.getFootballteamsData()


        liveComeBackData.addSource(teamData) { resource ->

            when (resource?.status) {


                Resource.AuthStatus.SUCCESS -> {
                    liveComeBackData.value = resource.data
                    liveLoading.value = false
                    liveConnect.value = resource.isConnected

                }

                Resource.AuthStatus.LOADING -> {
                    liveLoading.value = true
                }

                Resource.AuthStatus.ERROR -> {
                    liveComeBackData.value = resource.data
                    liveLoading.value = false
                    liveConnect.value = resource.isConnected
                }

            }
        }


    }


    fun getLiveFavorites(): LiveData<List<Favorites>?> = userRepository.getFavoritesData()

    fun insertFavorites(favoritesTeam: Favorites) {
        userRepository.insertFavorites(favoritesTeam)
    }

    fun deleteFavorite(id: Int) {
        userRepository.deleteFavorite(id)
    }


    override fun onCleared() {
        super.onCleared()
        jobs.forEach { if (it.isActive) it.cancel() }
    }
}