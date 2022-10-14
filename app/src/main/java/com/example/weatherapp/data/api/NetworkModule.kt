package com.example.weatherapp.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {

    private val moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
    .addNetworkInterceptor(httpLoggingInterceptor)
    .build()

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}