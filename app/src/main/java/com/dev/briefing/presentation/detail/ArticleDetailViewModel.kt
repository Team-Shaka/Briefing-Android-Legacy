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
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication.Companion.prefs
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

            Log.d(SERVER_TAG, "끝!")
        }
    }

    //TODO: 스크랩한 api 결과에 따른 분기처리 혹은 return 값 수정 필요
    fun setScrap() {
        viewModelScope.launch {
            try {
                val response = repository.setScrap(
                    memberInfo = SetScrapRequest(
                        memberId = 0,
                        briefingId = 0
                    )
                )
                Log.d(SERVER_TAG, "통신 끝")
                Log.d(SERVER_TAG, response.toString())
                Log.d(SERVER_TAG, "메소드 끝")
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
            }
        }
    }

    //TODO: 스크랩한 api 결과에 따른 분기처리 혹은 return 값 수정 필요
    fun setUnScrap() {
        viewModelScope.launch {
            try {
                val response = repository.unScrap(
                    memberId = memberId,
                    briefingId = id
                )
                Log.d(SERVER_TAG, "통신 끝")
                Log.d(SERVER_TAG, response.toString())
                Log.d(SERVER_TAG, "메소드 끝")
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
            }
        }
    }
}