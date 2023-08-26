package com.dev.briefing.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.dev.briefing.presentation.chat.ChatScreen
import com.dev.briefing.presentation.chat.ChattingScreen


fun NavGraphBuilder.chatNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.CHAT,
        startDestination = ChatScreen.Chat.route
    ) {
        composable(route = ChatScreen.Chat.route) {
            ChatScreen()
        }
        composable(route = ChatScreen.Chatting.route) {
            ChattingScreen()
        }

    }
}
sealed class ChatScreen(val route: String) {
    object Chat : ChatScreen(route = "CHAT")
    object Chatting : ChatScreen(route = "CHATTING")
}
