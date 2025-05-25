package com.shinjaehun.bottomnavigationdemo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.shinjaehun.bottomnavigationdemo.pages.HomePage
import com.shinjaehun.bottomnavigationdemo.pages.NotificationPage
import com.shinjaehun.bottomnavigationdemo.pages.SettingsPage

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Notification", Icons.Default.Notifications, 5),
        NavItem("Settings", Icons.Default.Settings),

    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
//                            Icon(imageVector = navItem.icon, contentDescription = "Icon")
                            BadgedBox(badge = {
                                if (navItem.badgeCount!=null) {
                                    // 그냥 null을 받지 말고
                                    // navItem.badgeCount>0 이렇게 비교
                                    Badge(){
                                        Text(text = navItem.badgeCount.toString())
                                    }
                                }
                            }) {
                                Icon(imageVector = navItem.icon, contentDescription = "Icon")
                            }
                        },
                        label = {
                            Text(text = navItem.label
                            )
                        }
                    )
                }
            }
        }

    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when(selectedIndex) {
        0 -> HomePage()
        1 -> NotificationPage()
        2 -> SettingsPage()
    }
}