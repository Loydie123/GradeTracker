package com.example.gradetracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Dashboard : BottomNavItem(
        route = Screen.Dashboard.route,
        title = "Dashboard",
        icon = Icons.Default.Dashboard
    )
    
    data object Subjects : BottomNavItem(
        route = Screen.SubjectList.route,
        title = "Subjects",
        icon = Icons.Default.MenuBook
    )
}
