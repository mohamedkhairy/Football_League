package com.example.footballleague.fragment.team

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.footballleague.database.entity.TeamInfoModel
import com.example.footballleague.network.ApiResponse
import com.example.footballleague.network.EndPoints
import javax.inject.Inject

class TeamInfoViewModel
@Inject
constructor(val endPoints: EndPoints, val app: Application) : AndroidViewModel(app) {


    val mediatorTeaminfo: MediatorLiveData<TeamInfoModel> = MediatorLiveData()

    private val liveError: MutableLiveData<Boolean> = MutableLiveData()

    private val liveLoading: MutableLiveData<Boolean> = MutableLiveData()


    fun getError(): LiveData<Boolean> = liveError

    fun getLoading(): LiveData<Boolean> = liveLoading

    fun getLiveTeamInfo(): LiveData<TeamInfoModel> = mediatorTeaminfo

    fun getTeamInfo(id: Int) {

        liveLoading.value = true

        val backData = endPoints.getTeamInfo(id.toString())

        mediatorTeaminfo.addSource(backData) { requestObjectApiResponse ->
            mediatorTeaminfo.removeSource(backData)


            when (requestObjectApiResponse) {
                is ApiResponse.ApiSuccessResponse<*> -> {

                    val data = processResponse(requestObjectApiResponse)
                    mediatorTeaminfo.value = data
                    liveLoading.value = false
                }

                is ApiResponse.ApiEmptyResponse<*> -> {
                    liveLoading.value = false
                    liveError.value = true
                }

                is ApiResponse.ApiErrorResponse<*> -> {
                    liveLoading.value = false
                    liveError.value = true
                }
            }


        }

    }


    private fun processResponse(response: ApiResponse.ApiSuccessResponse<*>): TeamInfoModel {
        return response.body as TeamInfoModel
    }
}