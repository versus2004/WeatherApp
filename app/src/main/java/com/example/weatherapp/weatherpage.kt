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
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

        var keycontrol = LocalSoftwareKeyboardController.current


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
             IconButton(onClick = { viewModel.getData(location)
                    keycontrol?.hide()
             }) {
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
            Text(text = data.location.region , fontSize = 18.sp)

        }
        Spacer(modifier =Modifier.height(16.dp))
        Text(text = "${ data.current.temp_c }°c" , fontSize = 59.sp , fontWeight = FontWeight.Bold,textAlign = TextAlign.Center)
        AsyncImage(modifier = Modifier.size(150.dp) , model = "https:${data.current.condition.icon}".replace("64x64","128x128"), contentDescription = "Icon")
        Text(text = data.current.condition.text, fontSize = 20.sp , fontWeight = FontWeight.Bold , textAlign = TextAlign.Center , color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Card {
            Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceAround) {

            weatherKeyValue("Humidity" ,data.current.humidity )
                        weatherKeyValue("Wind Speed" ,data.current.wind_kph+" km/h" )

                    }
                Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceAround) {

                    weatherKeyValue("UV " ,data.current.uv )
                    weatherKeyValue("Precipitation" ,data.current.precip_mm + " mm")

                }
                Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceAround) {

                    weatherKeyValue("Time" ,data.location.localtime.split(" ")[1] )
                    weatherKeyValue("Date" ,data.location.localtime.split(" ")[0] )

                }
            }
        }
    }

}

@Composable
fun weatherKeyValue(key: String ,   value  :String){
    Column(modifier = Modifier.padding(16.dp) , horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value , fontSize = 24.sp , fontWeight = FontWeight.Bold)
        Text(text = key , fontWeight = FontWeight.SemiBold , color = Color.Gray)
    }
}