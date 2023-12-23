package com.dev.briefing.presentation.detail

import com.dev.briefing.model.BriefingArticle

sealed interface ArticleDetailUiState {
    object Loading : ArticleDetailUiState
    data class Success(val article: BriefingArticle, val isScrapingInProgress: Boolean = false) : ArticleDetailUiState
    object Error : ArticleDetailUiState
}