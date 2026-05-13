package com.example.gradetracker.navigation

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object SubjectList : Screen("subjects")
    data object SubjectDetail : Screen("subject/{subjectId}") {
        fun createRoute(subjectId: Long) = "subject/$subjectId"
    }
}
