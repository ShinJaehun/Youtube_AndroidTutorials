package com.shinjaehun.firebaseauthenticationdemo.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shinjaehun.firebaseauthenticationdemo.AuthState
import com.shinjaehun.firebaseauthenticationdemo.AuthViewModel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {

//    val authState = authViewModel.authState.observeAsState()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

//    LaunchedEffect(authState.value) {
//        when(authState.value) {
//            is AuthState.UnAuthenticated -> navController.navigate("login")
//            else -> Unit
//        }
//    }

    LaunchedEffect(authState) {
        when(authState) {
            is AuthState.UnAuthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Page", fontSize = 32.sp)
        TextButton(onClick = {
            authViewModel.signout()
        }) {
            Text(text = "Sign out")

        }
    }
}