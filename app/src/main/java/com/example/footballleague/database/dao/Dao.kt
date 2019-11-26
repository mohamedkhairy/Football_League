package com.example.footballleague.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballleague.database.entity.Favorites
import com.example.footballleague.database.entity.Team

@Dao
interface Dao {

    ////////// home cache query /////////

    @Query("SELECT * from team_table")
    fun getSavedTeamsData(): LiveData<List<Team>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(data: List<Team>)

    @Query("UPDATE team_table SET isFavorites =:value WHERE id =:id")
    fun updateFav(value: Boolean, id: Int)


    @Query("DELETE FROM team_table")
    fun deleteAll()


    /////////// favorites query //////////

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorites(fav: Favorites)

    @Query("SELECT * from favorites_table")
    fun getSavedFavorites(): LiveData<List<Favorites>?>

    @Query("SELECT id from favorites_table")
    fun getFavoritesId(): List<Int>?

    @Query("DELETE FROM favorites_table WHERE id = :teamId")
    fun deleteTeam(teamId: Int)
}