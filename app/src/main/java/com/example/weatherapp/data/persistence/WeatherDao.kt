package com.example.weatherapp.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.weatherapp.data.model.Daily
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Transaction
    suspend fun updateForecast(daily: List<Daily>) {
        deleteDaily()
        insertDaily(daily)
    }

    @Insert
    suspend fun insertDaily(daily: List<Daily>)

    @Query("SELECT * FROM `daily` ORDER BY `time` ASC")
    fun getDailyList(): Flow<List<Daily>>

    @Query("SELECT * FROM `daily` WHERE time IS :key")
    fun getDaily(key: Long): Flow<Daily>

    @Query("DELETE FROM `daily`")
    suspend fun deleteDaily()

}