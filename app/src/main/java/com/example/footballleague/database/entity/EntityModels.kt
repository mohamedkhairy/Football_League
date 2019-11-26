package com.example.footballleague.database.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class CompetitionTeamsData(
    @SerializedName("competition")
    val competition: Competition,
    @SerializedName("count")
    val count: Int,
    @SerializedName("season")
    val season: Season,
    @SerializedName("teams")
    val teams: List<Team>
)

data class Competition(
    @SerializedName("area")
    val area: Area,
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastUpdated")
    val lastUpdated: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("plan")
    val plan: String
)

data class Area(
    @SerializedName("id")
    val areaId: Int,
    @SerializedName("name")
    val areaName: String
)


data class Season(
    @SerializedName("currentMatchday")
    val currentMatchday: Int,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("id")
    val seasonId: Int,
    @SerializedName("startDate")
    val startDate: String
)

@Entity(tableName = "team_table")
data class Team(
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,

    var isFavorites: Boolean = false,
    @SerializedName("address")
    val address: String?,
    @Embedded
    @SerializedName("area")
    val area: AreaX?,
    @SerializedName("clubColors")
    val clubColors: String?,
    @SerializedName("crestUrl")
    val crestUrl: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("founded")
    val founded: Int?,
    @SerializedName("lastUpdated")
    val lastUpdated: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("shortName")
    val shortName: String?,
    @SerializedName("tla")
    val tla: String?,
    @SerializedName("venue")
    val venue: String?,
    @SerializedName("website")
    val website: String?
)

data class AreaX(
    @SerializedName("id")
    val areaxId: Int?,
    @SerializedName("name")
    val areaxName: String?
)

////////////////////////////Team Info Model//////////////////

data class TeamInfoModel(
    @SerializedName("activeCompetitions")
    val activeCompetitions: List<ActiveCompetition>,
    @SerializedName("address")
    val address: String,
    @SerializedName("area")
    val area: TeamAreaX,
    @SerializedName("clubColors")
    val clubColors: String,
    @SerializedName("crestUrl")
    val crestUrl: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("founded")
    val founded: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastUpdated")
    val lastUpdated: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("shortName")
    val shortName: String,
    @SerializedName("squad")
    val squad: List<Squad>,
    @SerializedName("tla")
    val tla: String,
    @SerializedName("venue")
    val venue: String,
    @SerializedName("website")
    val website: String
)

data class ActiveCompetition(
    @SerializedName("area")
    val area: CompetitionArea,
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastUpdated")
    val lastUpdated: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("plan")
    val plan: String
)

data class CompetitionArea(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

data class TeamAreaX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

data class Squad(
    @SerializedName("countryOfBirth")
    val countryOfBirth: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("shirtNumber")
    val shirtNumber: Int
)

/////////////////////// Favorites Entity ////////////////////

@Entity(tableName = "favorites_table")
data class Favorites(
    @NonNull
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String?,
    val teamName: String?,
    val teamColor: String?,
    val venue: String?,
    val webSite: String?
)