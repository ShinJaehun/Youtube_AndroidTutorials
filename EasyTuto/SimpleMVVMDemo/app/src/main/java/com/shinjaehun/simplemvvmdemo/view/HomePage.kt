package com.shinjaehun.simplemvvmdemo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shinjaehun.simplemvvmdemo.viewmodel.HomeViewModel

@Composable
fun HomePage(modifier: Modifier = Modifier, viewModel: HomeViewModel) {

//    val userData = viewModel.userData.observeAsState()
//    val isLoading = viewModel.isLoading.observeAsState()

    val userData = viewModel.userData.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            viewModel.getUserData()
        }) {
            Text(text = "Get Data")
        }

        if (isLoading.value) {
            CircularProgressIndicator()
        } else {
            userData.value?.name?.let {
                Text(text = "Name $it")
            }
            userData.value?.age?.let {
                Text(text = "Age $it")
            }
        }
    }
}