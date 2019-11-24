package com.example.footballleague.fragment.team

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.footballleague.database.entity.TeamInfoModel
import com.example.footballleague.network.ApiResponse
import com.example.footballleague.network.EndPoints
import com.example.footballleague.repository.Repository
import com.example.footballleague.resource.Resource
import kotlinx.coroutines.*
import javax.inject.Inject

class TeamInfoViewModel
    @Inject
    constructor(val endPoints: EndPoints, val app: Application) : AndroidViewModel(app) {


    private val jobs: MutableList<Job> = mutableListOf()

    val mediatorTeaminfo: MediatorLiveData<TeamInfoModel> = MediatorLiveData()


    fun getLiveTeamInfo():LiveData<TeamInfoModel> = mediatorTeaminfo

    fun getTeamInfo(id: Int) {

        val backData = endPoints.getTeamInfo(id.toString())


        mediatorTeaminfo.addSource(backData) { requestObjectApiResponse ->
            mediatorTeaminfo.removeSource(backData)


            when (requestObjectApiResponse) {
                is ApiResponse.ApiSuccessResponse<*> -> {

//                    job += GlobalScope.launch(Dispatchers.Main) {
//
//                        }

                    val data = processResponse(requestObjectApiResponse)
                    mediatorTeaminfo.value  = data

                }


                is ApiResponse.ApiEmptyResponse<*> -> {
//                    job += GlobalScope.launch(Dispatchers.Main) {
//
//                    }

                    Log.d("xxxx" , "empty")

                }


                is ApiResponse.ApiErrorResponse<*> -> {

                    Log.d("xxxx" , requestObjectApiResponse.errorMessage!!)


                }
            }


        }

    }


    private fun processResponse(response: ApiResponse.ApiSuccessResponse<*>): TeamInfoModel {
        return response.body as TeamInfoModel
    }
}