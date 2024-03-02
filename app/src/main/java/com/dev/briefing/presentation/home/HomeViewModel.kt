package com.dev.briefing.presentation.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.data.respository.PushRepository
import com.dev.briefing.model.BriefingCategoryArticles
import com.dev.briefing.model.enum.BriefingArticleCategory
import com.google.firebase.messaging.FirebaseMessaging
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val briefingRepository: BriefingRepository,
    private val pushRepository: PushRepository
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
                    briefingArticleCategory
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

    fun subscribePushAlarm() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Logger.d("Fetching FCM registration token succeed : $token")

                viewModelScope.launch {
                    runCatching {
                        pushRepository.subscribePushAlarm(token)
                    }.onSuccess {
                        Logger.d("subscribePushAlarm Success")
                    }.onFailure {
                        Logger.e(it.message ?: "error in subscribePushAlarm")
                    }
                }
            } else {
                Logger.w("Fetching FCM registration token failed", task.exception)
            }
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