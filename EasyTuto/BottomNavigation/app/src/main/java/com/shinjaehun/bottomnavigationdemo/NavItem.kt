package com.shinjaehun.bottomnavigationdemo

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label : String,
    val icon: ImageVector,
    val badgeCount: Int? = null
)
