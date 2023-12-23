package com.dev.briefing.presentation.setting

import com.dev.briefing.model.BriefingArticle

sealed interface SettingUiState {
    object Default : SettingUiState
    object AccountDeletionCompleted : SettingUiState
}