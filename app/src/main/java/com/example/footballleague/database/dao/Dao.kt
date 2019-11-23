package com.example.footballleague.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballleague.database.entity.CompetitionTeamsData

@Dao
interface Dao {

    ////////// subscription cache query /////////

    @Query("SELECT * from competitions_table")
    fun getSavedTeamsData(): LiveData<CompetitionTeamsData?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(subData: CompetitionTeamsData)

    @Query("DELETE FROM competitions_table")
    fun deleteAll()

}