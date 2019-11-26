package com.example.footballleague.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.footballleague.database.dao.Dao
import com.example.footballleague.database.entity.CompetitionTeamsData
import com.example.footballleague.database.entity.Favorites
import com.example.footballleague.database.entity.Team
import com.example.footballleague.network.ApiResponse
import com.example.footballleague.network.EndPoints
import com.example.footballleague.resource.Resource
import com.example.footballleague.util.NetworkBoundResource
import javax.inject.Inject

class Repository
@Inject
constructor(val dao: Dao, val endPoints: EndPoints) {


    fun getFootballteamsData(): LiveData<Resource<List<Team>>> {
        return object : NetworkBoundResource<List<Team>, CompetitionTeamsData>() {

            override fun shouldFetch(data: List<Team>?): Boolean {
                return true
            }

            override fun makeApiCall(): LiveData<ApiResponse<CompetitionTeamsData?>> {

                return endPoints.getCompetitionTeams("PL")
            }

            override fun saveCallResult(data: CompetitionTeamsData) {
                dao.deleteAll()
                dao.insertAll(data.teams)
            }

            override fun loadFromDb(): LiveData<List<Team>?> {

                val savedId = dao.getFavoritesId()
                savedId?.forEach { id ->
                    Log.d("xxxxid", id.toString())
                    dao.updateFav(true, id)
                }

                return dao.getSavedTeamsData()
            }



        }.asLiveData()
    }




    fun getFavoritesId(): List<Int>? = dao.getFavoritesId()

    fun getFavoritesData(): LiveData<List<Favorites>?> = dao.getSavedFavorites()

    fun insertFavorites(favoritesTeam: Favorites){
        dao.insertFavorites(favoritesTeam)
        dao.updateFav(true, favoritesTeam.id)
    }

    fun deleteFavorite (id: Int) {
        dao.deleteTeam(id)
        dao.updateFav(false , id)
    }


}