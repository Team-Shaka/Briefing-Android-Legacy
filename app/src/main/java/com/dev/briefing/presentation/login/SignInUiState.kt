package com.dev.briefing.presentation.login

import com.dev.briefing.model.BriefingArticle

sealed interface SignInUiState {
    object Default : SignInUiState
    object Loading : SignInUiState
    object Success : SignInUiState
    object Error : SignInUiState
}