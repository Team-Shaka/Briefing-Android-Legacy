package com.dev.briefing.presentation.scrap

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.respository.ScrapRepository
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication
import com.dev.briefing.util.SERVER_TAG
import kotlinx.coroutines.launch

class ScrapViewModel(private val repository: ScrapRepository) : ViewModel() {
    private val _scrapList: MutableLiveData<MutableList<ScrapResponse>> =
        MutableLiveData<MutableList<ScrapResponse>>(mutableListOf())
    val scrapList: LiveData<MutableList<ScrapResponse>>
        get() = _scrapList
    val memberId:Int = MainApplication.prefs.getSharedPreference(MEMBER_ID,0)
    init {
        getScrapData()
    }

    /**
     * 스크랩한 기사들을 가져오는 메소드
     * [_scrapList] 업데이트
     */
    fun getScrapData() {
        viewModelScope.launch {
            try {
                val response = repository.getScrap(
                    memberId = memberId
                )
                Log.d(SERVER_TAG, "통신 끝")

                response.result.forEach { scrap ->
                    _scrapList.value?.add(scrap)
                }
                Log.d(SERVER_TAG, response.toString())
                Log.d(SERVER_TAG, "메소드 끝")
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
                // TODO: refresh Token
            }
        }
    }

}