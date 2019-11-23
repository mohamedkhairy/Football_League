package com.example.footballleague.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.footballleague.database.dao.Dao
import com.example.footballleague.database.entity.CompetitionTeamsData
import com.example.footballleague.network.ApiResponse
import com.example.footballleague.network.EndPoints
import com.example.footballleague.resource.Resource
import com.example.footballleague.util.NetworkBoundResource
import javax.inject.Inject

class Repository
@Inject
constructor(val dao: Dao, val endPoints: EndPoints) {


    fun getFootballteamsData(): LiveData<Resource<CompetitionTeamsData>> {
        return object : NetworkBoundResource<CompetitionTeamsData, CompetitionTeamsData>() {

            override fun shouldFetch(data: CompetitionTeamsData?): Boolean {
                return true
            }

            override fun makeApiCall(): LiveData<ApiResponse<CompetitionTeamsData?>> {
                Log.d("xxxx" ,"call")

                return endPoints.getCompetitionTeams("PL")
            }

            override fun saveCallResult(data: CompetitionTeamsData) {
                dao.deleteAll()
                dao.insertAll(data)
            }

            override fun loadFromDb(): LiveData<CompetitionTeamsData?> {
                Log.d("xxxx" ,"load")

                return dao.getSavedTeamsData()
            }

        }.asLiveData()
    }
}