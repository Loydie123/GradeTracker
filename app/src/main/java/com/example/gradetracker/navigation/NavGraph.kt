package com.example.gradetracker.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gradetracker.presentation.screen.DashboardScreen
import com.example.gradetracker.presentation.screen.SubjectDetailScreen
import com.example.gradetracker.presentation.screen.SubjectListScreen
import com.example.gradetracker.presentation.viewmodel.DashboardViewModel
import com.example.gradetracker.presentation.viewmodel.SubjectDetailViewModel
import com.example.gradetracker.presentation.viewmodel.SubjectDetailViewModelFactory
import com.example.gradetracker.presentation.viewmodel.SubjectListViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val application = LocalContext.current.applicationContext as Application
    
    NavHost(
        navController = navController,
        startDestination = Screen.SubjectList.route
    ) {
        composable(Screen.Dashboard.route) {
            val viewModel: DashboardViewModel = viewModel()
            DashboardScreen(viewModel = viewModel)
        }
        
        composable(Screen.SubjectList.route) {
            val viewModel: SubjectListViewModel = viewModel()
            SubjectListScreen(
                viewModel = viewModel,
                onSubjectClick = { subjectId ->
                    navController.navigate(Screen.SubjectDetail.createRoute(subjectId))
                }
            )
        }
        
        composable(
            route = Screen.SubjectDetail.route,
            arguments = listOf(
                navArgument("subjectId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getLong("subjectId") ?: return@composable
            val viewModel: SubjectDetailViewModel = viewModel(
                factory = SubjectDetailViewModelFactory(application, subjectId)
            )
            SubjectDetailScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
