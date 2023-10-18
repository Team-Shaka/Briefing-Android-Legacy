package com.dev.briefing.presentation.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.R
import com.dev.briefing.data.NewsContent
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.data.respository.ScrapRepository
import com.dev.briefing.util.JWT_TOKEN
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication.Companion.prefs
import com.dev.briefing.util.REFRESH_TOKEN
import com.dev.briefing.util.SERVER_TAG
import com.dev.briefing.util.SharedPreferenceHelper
import kotlinx.coroutines.launch

class ArticleDetailViewModel(private val repository: BriefingRepository, private val id: Int) :
    ViewModel() {
    private val _detailPage: MutableLiveData<BriefingDetailResponse> =
        MutableLiveData<BriefingDetailResponse>()
    val detailPage: LiveData<BriefingDetailResponse>
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

    fun getBrieingId(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getBriefingDetail(
                    id = id
                )
                if (response != null) {
                    _detailPage.value = response.result!!
                } else {
                    Log.d(SERVER_TAG, "response null")
                }
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
            }

        }
    }

    //TODO: 스크랩한 api 결과에 따른 분기처리 혹은 return 값 수정 필요
    fun setScrap(): () -> Unit = {
        viewModelScope.launch {
            try {
                val response = repository.setScrap(
                    memberInfo = SetScrapRequest(
                        memberId = memberId,
                        briefingId = id
                    )
                )
                Log.d(SERVER_TAG, "메세지:${response.message} 코드 : ${response.code}")

                if (!response.isSuccess) {
                    _statusMsg.value = response.message
                    getAcessToken(prefs.getSharedPreference(REFRESH_TOKEN, ""))
                }
                Log.d(SERVER_TAG, response.message)
            } catch (e: Throwable) {
                _statusMsg.value = e.message
                Log.e(SERVER_TAG, "메세지:${e.message} 코드 : ${e.localizedMessage}")
                if(e.message?.contains("401") != null){
                    getAcessToken(prefs.getSharedPreference(REFRESH_TOKEN, ""))
                }

            }
        }
    }

    //TODO: 스크랩한 api 결과에 따른 분기처리 혹은 return 값 수정 필요
    fun unScrap(): () -> Unit = {
        viewModelScope.launch {
            try {
                val response = repository.unScrap(
                    memberId = memberId,
                    briefingId = id
                )
                if (!response.isSuccess) {
                    _statusMsg.value = response.message
                }
            } catch (e: Throwable) {
                Log.e(SERVER_TAG, e.toString())
                _statusMsg.value = e.localizedMessage
            }
        }
    }

    fun getAcessToken(refreshToken: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAccessToken(
                    com.dev.briefing.data.model.TokenRequest(
                        refreshToken = refreshToken
                    )
                )
                if (response.result.accessToken != null) {
                    prefs.putSharedPreference(JWT_TOKEN, response.result.accessToken)
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