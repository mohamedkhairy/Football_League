package com.example.footballleague.fragment.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.example.footballleague.database.entity.CompetitionTeamsData
import com.example.footballleague.repository.Repository
import com.example.footballleague.resource.Resource
import kotlinx.coroutines.Job
import javax.inject.Inject

class HomeViewModel
@Inject
constructor(private val userRepository: Repository, val app: Application) : AndroidViewModel(app) {


    private val jobs: MutableList<Job> = mutableListOf()

    private val liveComeBackData: MediatorLiveData<CompetitionTeamsData> = MediatorLiveData()

    private val liveLoading: MutableLiveData<Boolean> = MutableLiveData()


    fun getLoading(): LiveData<Boolean> = liveLoading

    fun getTeamsData():LiveData<CompetitionTeamsData> = liveComeBackData


    fun getFootballTeams(){

        val teamData = userRepository.getFootballteamsData()


        liveComeBackData.addSource(teamData){resource ->

            when (resource?.status) {


                Resource.AuthStatus.SUCCESS -> {
                    Log.d("xxxx" ,"SUCCESS")
                    liveComeBackData.value = resource.data
                }

                Resource.AuthStatus.LOADING -> {
                    Log.d("xxxx" ,"LOADING")
                    liveLoading.value = true

                }

                Resource.AuthStatus.ERROR -> {
                    Log.d("xxxx" ,"ERROR")

                    liveComeBackData.value = resource.data
                }


            }
        }



    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { if (it.isActive) it.cancel() }
    }
}