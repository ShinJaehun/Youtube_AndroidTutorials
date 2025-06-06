package com.shinjaehun.onlineshopappdemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.shinjaehun.onlineshopappdemo.pages.CategoryProductsPage
import com.shinjaehun.onlineshopappdemo.pages.CheckoutPage
import com.shinjaehun.onlineshopappdemo.pages.ProductDetailsPage
import com.shinjaehun.onlineshopappdemo.screen.AuthScreen
import com.shinjaehun.onlineshopappdemo.screen.HomeScreen
import com.shinjaehun.onlineshopappdemo.screen.LoginScreen
import com.shinjaehun.onlineshopappdemo.screen.SignupScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    GlobalNavigation.navController = navController
    val isLoggedIn = Firebase.auth.currentUser != null
    val firstPage = if (isLoggedIn) "home" else "auth"

    NavHost(
        navController = navController,
        startDestination = firstPage
    ) {
        composable("auth") {
            AuthScreen(modifier, navController)
        }
        composable("login") {
            LoginScreen(modifier, navController)
        }
        composable("signup") {
            SignupScreen(modifier, navController)
        }
        composable("home") {
            HomeScreen(modifier, navController)
        }
        composable("category-products/{categoryId}") {
            val categoryId = it.arguments?.getString("categoryId")
            CategoryProductsPage(modifier, categoryId?:"")
        }
        composable("product-details/{productId}") {
            val productId = it.arguments?.getString("productId")
            ProductDetailsPage(modifier, productId?:"")
        }
        composable("checkout") {
            CheckoutPage(modifier)
        }
    }
}

object GlobalNavigation{
    lateinit var navController: NavHostController
}