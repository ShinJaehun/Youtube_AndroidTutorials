package com.shinjaehun.onlineshopappdemo.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.shinjaehun.onlineshopappdemo.AppUtil
import com.shinjaehun.onlineshopappdemo.GlobalNavigation.navController

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {

    var name by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                name = it.result.get("name").toString()
            }
    }

    Column(
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ){

        Text(text = "Welcome back")
        Text(
            text = name,
            style = TextStyle(
                fontSize=18.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier=Modifier.height(16.dp))
        Button(
            onClick = {
                Firebase.auth.signOut()
                navController.navigate("auth") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "Log out", fontSize = 16.sp)
        }

    }

}