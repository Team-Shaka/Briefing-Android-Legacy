package com.dev.briefing.navigation

import BriefingHome
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dev.briefing.ui.article.ArticleDetail
import com.dev.briefing.ui.article.ArticleScreen
import com.dev.briefing.ui.chat.ChatScreen
import com.dev.briefing.ui.scrap.ScrapScreen
import com.dev.briefing.ui.setting.SettingScreen



fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.HOME,
        startDestination = HomeScreen.Home.route
    ) {
        composable(route = HomeScreen.Home.route) {
            BriefingHome(
                onSettingClick = {
                    navController.navigate(HomeScreen.Setting.route)
                },
                onScrapClick = {
                    navController.navigate(HomeScreen.Scrap.route)
                },
            )
        }
        composable(route = HomeScreen.Setting.route) {
//            SettingScreen(
//                onBackClick = {
//                    navController.popBackStack()
//                },
//            )
            ArticleScreen(
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
        composable(route = HomeScreen.Scrap.route) {
            ScrapScreen(
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

}