package com.dev.briefing.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.datasource.BriefingDataSource
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.respository.AuthRepository
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.data.respository.ScrapRepository
import com.dev.briefing.model.BriefingArticle
import com.dev.briefing.util.JWT_TOKEN
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication.Companion.prefs
import com.dev.briefing.util.REFRESH_TOKEN
import com.dev.briefing.util.SERVER_TAG
import kotlinx.coroutines.launch

class ArticleDetailViewModel(private val repository: BriefingRepository, private val scrapRepository: ScrapRepository, private val authRepository : AuthRepository, private val id: Int) :
    ViewModel() {
    private val _detailPage: MutableLiveData<BriefingArticle> =
        MutableLiveData<BriefingArticle>()
    val detailPage: MutableLiveData<BriefingArticle>
        get() = _detailPage
    val memberId: Int = prefs.getSharedPreference(MEMBER_ID, 0)

    private val _authError: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val authError: LiveData<Boolean>
        get() = _authError

    private val _statusMsg: MutableLiveData<String> = MutableLiveData<String>("")
    val statusMsg: LiveData<String>
        get() = _statusMsg

    init {
        getBrieingId(id)
    }

    private fun getBrieingId(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getBriefingDetail(
                    id = id
                )
                if (response != null) {
                    _detailPage.value = response
                } else {
                    Log.d(SERVER_TAG, "response null")
                }
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
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

    fun getAcessToken(refreshToken: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.getAccessToken(
                    com.dev.briefing.data.model.TokenRequest(
                        refreshToken = refreshToken
                    )
                )
                if (response.result.accessToken != null) {
                    prefs.putSharedPreference(JWT_TOKEN, response.result.accessToken)
                    prefs.putSharedPreference(REFRESH_TOKEN, response.result.refreshToken)
                    Log.d(SERVER_TAG, "토큰 재발급 성공")
                }
                Log.d(SERVER_TAG, response.code)
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
                _authError.value = true
            }
        }
    }
}