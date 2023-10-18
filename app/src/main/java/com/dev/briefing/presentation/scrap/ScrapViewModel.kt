package com.dev.briefing.presentation.scrap

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.ScrapViewData
import com.dev.briefing.data.respository.ScrapRepository
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication
import com.dev.briefing.util.SERVER_TAG
import kotlinx.coroutines.launch

class ScrapViewModel(private val repository: ScrapRepository) : ViewModel() {

    private val _scrapMap: MutableLiveData<MutableMap<String, List<ScrapResponse>>> =
        MutableLiveData<MutableMap<String, List<ScrapResponse>>>(mutableMapOf())
    val scrapMap: LiveData<MutableMap<String, List<ScrapResponse>>>
        get() = _scrapMap

    val memberId: Int = MainApplication.prefs.getSharedPreference(MEMBER_ID, 0)

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
                var tmpMap : MutableMap<String, List<ScrapResponse>> = mutableMapOf()
                response.result.forEach { scrap ->
                    val currentList = tmpMap.get(scrap.date)
                        ?: emptyList() // 해당 키의 리스트를 가져오고, null일 경우 빈 리스트로 초기화
                    val updatedList = currentList + scrap
                    tmpMap.set(scrap.date, updatedList)
                }
                _scrapMap.value = tmpMap.toSortedMap(compareByDescending {
                    SimpleDateFormat("yyyy-MM-dd").parse(it)
                })
                // TODO: Log상에서는 정렬이 되는데 
                _scrapMap.value?.forEach { (key, value) ->
                    Log.d(SERVER_TAG, "Key: $key, Value: $value")
                }
                Log.d(SERVER_TAG, response.toString())
                Log.d(SERVER_TAG, "메소드 끝")
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
                // TODO: refresh Token
            }
        }
    }
    fun getAcessToken(refreshToken:String) {
        viewModelScope.launch {
            try {
                val response = repository.getAccessToken(
                    com.dev.briefing.data.model.TokenRequest(
                        refreshToken = refreshToken
                    )
                )
                Log.d(SERVER_TAG, response.code)
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
            }
        }
    }

}