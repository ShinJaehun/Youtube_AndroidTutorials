package com.shinjaehun.fashionapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.contentValuesOf
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shinjaehun.fashionapp.R
import com.shinjaehun.fashionapp.Screen

@Composable
fun LandingScreen(
    navController: NavController,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.landing),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(4.dp)
                    .wrapContentWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                   Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Menu",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentWidth()
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clickable { navController.navigate(Screen.Dashboard.route) }
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp
                        )
                    )
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.wrapContentWidth()
                )
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun LandingScreenPreview() {
    LandingScreen(navController = rememberNavController())
}