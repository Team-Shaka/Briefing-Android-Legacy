package com.dev.briefing.navigation

import BriefingHomeScreen
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.dev.briefing.presentation.detail.ArticleDetailScreen
import com.dev.briefing.presentation.scrap.ScrapScreen
import com.dev.briefing.presentation.setting.PremiumScreen
import com.dev.briefing.presentation.setting.SettingScreen


fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.HOME,
        startDestination = HomeScreen.Home.route,
    ) {
        composable(route = HomeScreen.Home.route) {
            BriefingHomeScreen(
                onSettingClick = {
                    navController.navigate(HomeScreen.Setting.route)
                },
                navController = navController
            )
        }
        composable(
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            route = HomeScreen.Detail.route + "/{id}"
        )
        { backStackEntry ->
            val id: Long = backStackEntry.arguments?.getLong("id") ?: 0
            ArticleDetailScreen(
                articleId = id,
                onBackClick = {
                    navController.popBackStack()
                },
                navController = navController
            )
        }
        composable(route = HomeScreen.Scrap.route) {
            ScrapScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                navController = navController
            )
        }
        composable(route = HomeScreen.Setting.route) {
            SettingScreen(
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
        composable(route = HomeScreen.Premium.route) {
            PremiumScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class HomeScreen(val route: String) {
    object Home : HomeScreen(route = "HOME")
    object Setting : HomeScreen(route = "SETTING")
    object Premium : HomeScreen(route = "PREMIUM")
    object Scrap : HomeScreen(route = "SCRAP")
    object Detail : HomeScreen(route = "DETAIL")
}