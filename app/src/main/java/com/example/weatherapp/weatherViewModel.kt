package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.ViewModel

class weatherViewModel: ViewModel() {




    fun getData(city : String){
        Log.d("city" , city)

    }
}