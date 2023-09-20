package com.dev.briefing.navigation

import BriefingHome
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.dev.briefing.presentation.detail.ArticleDetailScreen
import com.dev.briefing.presentation.scrap.ScrapScreen
import com.dev.briefing.presentation.setting.SettingScreen



fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.HOME,
        startDestination = HomeScreen.Home.route,
    ) {
        composable(route = HomeScreen.Home.route) {
            BriefingHome(
                onSettingClick = {
                    navController.navigate(HomeScreen.Setting.route)
                },
                onScrapClick = {
                    navController.navigate(HomeScreen.Scrap.route)
                },
                navController = navController
            )
        }
        composable(
            arguments = listOf( navArgument("id") { type = NavType.IntType }),
            route = HomeScreen.Detail.route+"/{id}")
            {backStackEntry ->
            var id: Int = backStackEntry.arguments?.getInt("id")?: 0
            ArticleDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                id = id
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
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}

sealed class HomeScreen(val route: String) {
    object Home : HomeScreen(route = "HOME")
    object Setting : HomeScreen(route = "SETTING")
    object Scrap : HomeScreen(route = "SCRAP")
    object Detail : HomeScreen(route = "DETAIL")

}