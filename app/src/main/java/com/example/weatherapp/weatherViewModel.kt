package com.example.weatherapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.api.WeatherModel
import kotlinx.coroutines.launch

class weatherViewModel: ViewModel() {

        private val weatherApi : WeatherApi = RetrofitInstance.weatherApi
    private val _weatherResult : MutableLiveData<NetworkResponse<WeatherModel>> = MutableLiveData()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> get() = _weatherResult


    fun getData(city : String){
        _weatherResult.value = NetworkResponse.Loading
            viewModelScope.launch {


                try {
                    val response=    weatherApi.getWeather(constant.apikey,city)


                    if (response.isSuccessful){
                        response.body()?.let {
                            _weatherResult.value = NetworkResponse.success(it)
                        }

                    }
                    else{
                        _weatherResult.value = NetworkResponse.error("Failed to Load Data")
                    }
                }
                catch (e:Exception){
                  _weatherResult.value = NetworkResponse.error("Failed to load data")

                }



        }

    }
}

