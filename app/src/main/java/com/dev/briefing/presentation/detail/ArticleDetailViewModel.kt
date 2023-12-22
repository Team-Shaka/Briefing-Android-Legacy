package com.dev.briefing.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.respository.AuthRepository
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.data.respository.ScrapRepository
import com.dev.briefing.util.preference.AuthPreferenceHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    private val briefingRepository: BriefingRepository,
    private val scrapRepository: ScrapRepository,
    private val authPreferenceHelper: AuthPreferenceHelper
) : ViewModel() {

    private val _briefingArticleState =
        MutableStateFlow<ArticleDetailUiState>(ArticleDetailUiState.Loading)

    val briefingArticleState: StateFlow<ArticleDetailUiState> =
        _briefingArticleState.asStateFlow()

    fun loadBriefingArticle(id: Long) {
        viewModelScope.launch {
            runCatching {
                briefingRepository.getBriefingDetail(id)
            }.onSuccess { article ->
                _briefingArticleState.update {
                    ArticleDetailUiState.Success(article)
                }
            }.onFailure {
                Log.e("ArticleDetailViewModel", it.message.toString())
                _briefingArticleState.update {
                    ArticleDetailUiState.Error
                }
            }
        }
    }

    fun setScrap(articleId: Long) {
        val memberId = authPreferenceHelper.getMemberId()
        if (memberId == -1) { return }

        _briefingArticleState.update {
            if (it is ArticleDetailUiState.Success) {
                ArticleDetailUiState.Success(it.article, true)
            } else {
                ArticleDetailUiState.Loading
            }
        }

        viewModelScope.launch {
            runCatching {
                scrapRepository.setScrap(memberId, articleId)
                briefingRepository.getBriefingDetail(articleId)
            }.onSuccess { article ->
                _briefingArticleState.update {
                    ArticleDetailUiState.Success(article)
                }
            }.onFailure {
                _briefingArticleState.update {
                    if (it is ArticleDetailUiState.Success) {
                        ArticleDetailUiState.Success(
                            article = it.article,
                            isScrapingInProgress = false
                        )
                    } else {
                        ArticleDetailUiState.Error
                    }
                }
            }
        }
    }

    fun unScrap(articleId: Long) {
        val memberId = authPreferenceHelper.getMemberId()
        if (memberId == -1) { return }

        _briefingArticleState.update {
            if (it is ArticleDetailUiState.Success) {
                ArticleDetailUiState.Success(it.article, true)
            } else {
                ArticleDetailUiState.Loading
            }
        }

        viewModelScope.launch {
            runCatching {
                scrapRepository.unScrap(memberId, articleId)
                briefingRepository.getBriefingDetail(articleId)
            }.onSuccess { article ->
                _briefingArticleState.update {
                    ArticleDetailUiState.Success(article)
                }
            }.onFailure {
                _briefingArticleState.update {
                    if (it is ArticleDetailUiState.Success) {
                        ArticleDetailUiState.Success(
                            article = it.article,
                            isScrapingInProgress = false
                        )
                    } else {
                        ArticleDetailUiState.Error
                    }
                }
            }
        }
    }


//
//    //TODO: 스크랩한 api 결과에 따른 분기처리 혹은 return 값 수정 필요
//    fun setScrap(): () -> Boolean = {
//        viewModelScope.launch {
//            try {
//                val response = scrapRepository.getScrap(
//                    memberInfo = SetScrapRequest(
//                        memberId = memberId,
//                        briefingId = id
//                    )
//                )
//                Log.d(SERVER_TAG, "메세지:${response.message} 코드 : ${response.code}")
//
//                if (!response.isSuccess) {
//                    _statusMsg.value = response.message
//                    getAcessToken(prefs.getSharedPreference(REFRESH_TOKEN, ""))
//                    false
//                }
//                Log.d(SERVER_TAG, response.message)
//                true
//            } catch (e: Throwable) {
//                _statusMsg.value = e.message
//                Log.e(SERVER_TAG, "메세지:${e.message} 코드 : ${e.localizedMessage}")
//                if (e.message?.contains("401") != null) {
//                    getAcessToken(prefs.getSharedPreference(REFRESH_TOKEN, ""))
//                }
//                false
//
//            }
//
//        }
//        true
//    }
//
//    //TODO: 스크랩한 api 결과에 따른 분기처리 혹은 return 값 수정 필요
//    fun unScrap(): () -> Boolean = {
//        viewModelScope.launch {
//            try {
//                val response = repository.unScrap(
//                    memberId = memberId,
//                    briefingId = id
//                )
//                if (response.isSuccess) {
//                    true
//                } else {
//                    _statusMsg.value = response.message
//                    getAcessToken(prefs.getSharedPreference(REFRESH_TOKEN, ""))
//                    false
//                }
//            } catch (e: Throwable) {
//                Log.e(SERVER_TAG, e.toString())
//                _statusMsg.value = e.localizedMessage
//                false
//            }
//        }
//        true
//    }

}