package com.example.footballleague.network

import androidx.lifecycle.LiveData
import com.example.footballleague.database.entity.CompetitionTeamsData
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoints {

    @GET("/v2/competitions/{id}/teams")
    fun getCompetitionTeams(@Path("id") id: String): LiveData<ApiResponse<CompetitionTeamsData?>>
}