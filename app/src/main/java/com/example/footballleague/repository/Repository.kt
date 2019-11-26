package com.example.footballleague.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
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
constructor(val dao: Dao, val endPoints: EndPoints, val application: Application) {


    fun getFootballteamsData(): LiveData<Resource<List<Team>>> {

        return object : NetworkBoundResource<List<Team>, CompetitionTeamsData>() {

            override fun shouldFetch(data: List<Team>?): Boolean {

                if (isNetworkAvailable()) {
                    return true
                } else
                    return false
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
                    dao.updateFav(true, id)
                }

                return dao.getSavedTeamsData()
            }


        }.asLiveData()
    }


    private fun isNetworkAvailable(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun getFavoritesData(): LiveData<List<Favorites>?> = dao.getSavedFavorites()

    fun insertFavorites(favoritesTeam: Favorites) {
        dao.insertFavorites(favoritesTeam)
        dao.updateFav(true, favoritesTeam.id)
    }

    fun deleteFavorite(id: Int) {
        dao.deleteTeam(id)
        dao.updateFav(false, id)
    }


}