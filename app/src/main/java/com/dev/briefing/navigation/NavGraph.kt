package com.dev.briefing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController=navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME,
    ) {
        homeNavGraph(navController = navController)
        chatNavGraph(navController = navController)
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val CHAT = "chat_graph"
}