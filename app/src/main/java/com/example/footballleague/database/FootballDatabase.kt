package com.example.footballleague.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.footballleague.converter.TeamConverter
import com.example.footballleague.database.dao.Dao
import com.example.footballleague.database.entity.CompetitionTeamsData

@Database(entities = [CompetitionTeamsData::class], version = 1)
@TypeConverters(value = [(TeamConverter::class)])
abstract class FootballDatabase: RoomDatabase() {

    abstract fun footballDao(): Dao


    companion object {

        private var instance: FootballDatabase? = null

        @Synchronized
        fun getInstance(context: Context): FootballDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FootballDatabase::class.java, "football.db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}