package com.example.weatherapp.api

sealed class NetworkResponse<out T> {
data class success<out T>(val data :T ) : NetworkResponse<T>()
    data class error(val message : String): NetworkResponse<Nothing>()
        object Loading : NetworkResponse<Nothing>()

}