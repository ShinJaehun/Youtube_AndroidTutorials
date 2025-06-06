package com.shinjaehun.onlineshopappdemo.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.shinjaehun.onlineshopappdemo.pages.CartPage
import com.shinjaehun.onlineshopappdemo.pages.FavoritePage
import com.shinjaehun.onlineshopappdemo.pages.HomePage
import com.shinjaehun.onlineshopappdemo.pages.ProfilePage

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
//    Column(
//        modifier = modifier.fillMaxSize()
//            .padding(32.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//        Text(text = "HomeScreen")
//        Button(
//            onClick = {
//                Firebase.auth.signOut()
//                navController.navigate("auth") {
//                    popUpTo("home") { inclusive = true }
//                }
//            }
//        ) {
//            Text(text = "Log out")
//        }
//    }
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Favorite", Icons.Default.Favorite),
        NavItem("Cart", Icons.Default.ShoppingCart),
        NavItem("Profile", Icons.Default.Person),
    )
//    var selectedIndex by remember {
//        mutableStateOf(0)
//    }

    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
//                Text(text = "Test bottom bar")
//                NavigationBarItem(
//                    selected = false,
//                    onClick = {
//
//                    },
//                    icon = {
//                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "cart")
//                    }
//                )
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) {
        ContentScreen(modifier = modifier.padding(it), selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when(selectedIndex) {
        0 -> HomePage(modifier)
        1 -> FavoritePage(modifier)
        2 -> CartPage(modifier)
        3 -> ProfilePage(modifier)
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector
)
