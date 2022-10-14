package com.example.weatherapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val zip: String
)