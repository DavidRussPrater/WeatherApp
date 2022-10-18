package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.ForecastDTO
import com.example.weatherapp.data.model.Location
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/onecall?units=imperial&exclude=minutely")
    suspend fun getForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String = "imperial",
        @Query("appid") apiToken: String = "803c7f2de098a5ee7b414419d784b598"
    ): ForecastDTO

    @GET("geo/1.0/zip?")
    suspend fun getZipCode(
        @Query("zip") zipCode: String,
        @Query("appid") apiToken: String = "803c7f2de098a5ee7b414419d784b598"
    ): Location

}