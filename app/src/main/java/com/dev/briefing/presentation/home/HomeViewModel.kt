package com.dev.briefing.presentation.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.model.BriefingCategoryArticles
import com.dev.briefing.model.BriefingCompactArticle
import com.dev.briefing.model.enum.BriefingArticleCategory
import com.dev.briefing.model.enum.TimeOfDay
import com.dev.briefing.util.dailyalert.DailyAlertManager
import com.dev.briefing.util.preference.DailyAlertTimePreferenceHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel(
    private val briefingRepository: BriefingRepository,
    private val dailyAlertManager: DailyAlertManager,
    private val dailyAlertTimePreferenceHelper: DailyAlertTimePreferenceHelper
) : ViewModel() {
    private val errorOccurCategories: SnapshotStateMap<BriefingArticleCategory, String> =
        mutableStateMapOf()
    private val currentLoadingCategories: MutableSet<BriefingArticleCategory> = mutableSetOf()
    private val briefingArticles: SnapshotStateMap<BriefingArticleCategory, BriefingCategoryArticles> =
        mutableStateMapOf()

    private val _briefingArticlesState =
        MutableStateFlow(HomeBriefingArticleUiState())

    val briefingArticleUiState: StateFlow<HomeBriefingArticleUiState> =
        _briefingArticlesState.asStateFlow()

    fun loadBriefings(
        briefingArticleCategory: BriefingArticleCategory,
        isRefreshRequest: Boolean = false
    ) {
        if ((currentLoadingCategories.contains(briefingArticleCategory) || briefingArticles.contains(
                briefingArticleCategory
            )) && !isRefreshRequest
        ) return

        viewModelScope.launch {
            currentLoadingCategories.add(briefingArticleCategory)
            updateUiState()

            runCatching {
                briefingRepository.getBriefings(
                    briefingArticleCategory,
                    LocalDate.of(2023, 11, 30),
                    TimeOfDay.MORNING
                )
            }.onSuccess {
                currentLoadingCategories.remove(briefingArticleCategory)
                briefingArticles[briefingArticleCategory] = it
                updateUiState()
            }.onFailure {
                currentLoadingCategories.remove(briefingArticleCategory)
                errorOccurCategories[briefingArticleCategory] = it.message ?: "unknown error"
                updateUiState()
            }
        }
    }

    fun setAlarm() {
        dailyAlertTimePreferenceHelper.getAlarmTime().also {
            dailyAlertManager.setDailyAlarm(it.hour, it.minute)
        }
    }

    private fun updateUiState() {
        _briefingArticlesState.update {
            it.copy(
                currentLoadingCategories = currentLoadingCategories.toMutableSet(),
                errorOccurCategories = errorOccurCategories.toMutableMap(),
                briefingArticles = briefingArticles.toMutableMap()
            )
        }
    }
}