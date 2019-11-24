package com.example.footballleague.database.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "competitions_table")
data class CompetitionTeamsData(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val key: Int,
    @Embedded
    @SerializedName("competition")
    val competition: Competition,
    @SerializedName("count")
    val count: Int,
    @Embedded
    @SerializedName("season")
    val season: Season,
    @SerializedName("teams")
    val teams: List<Team>
)

data class Competition(
    @Embedded
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

data class Team(
    @SerializedName("address")
    val address: String,
    @SerializedName("area")
    val area: AreaX,
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
    @SerializedName("tla")
    val tla: String,
    @SerializedName("venue")
    val venue: String,
    @SerializedName("website")
    val website: String
)

data class AreaX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
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