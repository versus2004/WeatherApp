package com.example.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.WeatherModel
import com.example.weatherapp.weatherViewModel

@Composable
fun weatherPage(viewModel: weatherViewModel){
    var location by remember{
        mutableStateOf("")
    }
    val weatheres = viewModel.weatherResult.observeAsState()




    Column(modifier = Modifier
        .systemBarsPadding()
        .fillMaxWidth()
        .padding(8.dp) , horizontalAlignment = Alignment.CenterHorizontally) {
         Row(modifier = Modifier
             .fillMaxWidth()
             .padding(8.dp), verticalAlignment = Alignment.CenterVertically ,
             horizontalArrangement = Arrangement.SpaceEvenly) {
             OutlinedTextField(value = location, onValueChange = {location=it} ,
                 label = { Text(text = "Search for the city")} , modifier = Modifier.weight(1f))
             IconButton(onClick = { viewModel.getData(location) }) {
                 Icon(imageVector = Icons.Default.Search, contentDescription = "Search for location")

             }
         }
        when(val result =           weatheres.value){
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.error -> {
                Text(text = result.message)
            }
            is NetworkResponse.success ->{
               weathershow(data = result.data)}
            null -> {}
        }

    }
}

@Composable
fun weathershow(data : WeatherModel){

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp) , horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start , verticalAlignment = Alignment.Bottom) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Current Location" , modifier = Modifier.size(40.dp))
            Text(text = data.location.name , fontSize = 30.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country , fontSize = 18.sp)

        }
        Spacer(modifier =Modifier.height(16.dp))
        Text(text = "${ data.current.temp_c }°c" , fontSize = 59.sp , fontWeight = FontWeight.Bold)
        AsyncImage(modifier = Modifier.size(150.dp) , model = "https:${data.current.condition.icon}", contentDescription = "Icon")

    }

}