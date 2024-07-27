package com.example.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.weatherViewModel
@Composable
fun weatherPage(viewModel: weatherViewModel){
    var location by remember{
        mutableStateOf("")
    }
    Column(modifier = Modifier.systemBarsPadding().fillMaxWidth().padding(8.dp) , horizontalAlignment = Alignment.CenterHorizontally) {
         Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically ,
             horizontalArrangement = Arrangement.SpaceEvenly) {
             OutlinedTextField(value = location, onValueChange = {location=it} ,
                 label = { Text(text = "Search for the city")} , modifier = Modifier.weight(1f))
             IconButton(onClick = { viewModel.getData(location) }) {
                 Icon(imageVector = Icons.Default.Search, contentDescription = "Search for location")

             }
         }
    }
}