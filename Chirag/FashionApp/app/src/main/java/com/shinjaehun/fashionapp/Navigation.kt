package com.shinjaehun.fashionapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shinjaehun.fashionapp.screens.DashboardScreen
import com.shinjaehun.fashionapp.screens.LandingScreen
import com.shinjaehun.fashionapp.screens.ProductScreen
import com.shinjaehun.fashionapp.screens.ProductSize

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Landing.route
    ) {
        composable(route = Screen.Landing.route) {
            LandingScreen(navController = navController)
        }
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(route = Screen.Product.route) {
            ProductScreen(navController = navController)
        }
    }

}