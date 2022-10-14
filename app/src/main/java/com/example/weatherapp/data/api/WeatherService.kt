package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast?appid=1910a41da709ed1c53440dfe9290cfd1")
    suspend fun getForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String = "imperial"
    ): Forecast

}