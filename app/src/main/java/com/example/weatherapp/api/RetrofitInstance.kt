package com.example.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val baseurl= "https://api.weatherapi.com";

    private fun getinstance() : Retrofit{
        return Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build()
    }
    val weatherApi: WeatherApi = getinstance().create(WeatherApi::class.java)
}