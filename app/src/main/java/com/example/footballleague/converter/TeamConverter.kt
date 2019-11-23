package com.example.footballleague.converter

import androidx.room.TypeConverter
import com.example.footballleague.database.entity.Team
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TeamConverter {

    @TypeConverter
    fun fromString(value: String): List<Team>? {
        val listType = object : TypeToken<List<Team>>() {}.type
        return Gson().fromJson<List<Team>>(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Team>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}