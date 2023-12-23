package com.dev.briefing.presentation.scrap

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.respository.ScrapRepository
import com.dev.briefing.model.Scrap
import com.dev.briefing.model.toScrap
import com.dev.briefing.util.MainApplication
import com.dev.briefing.util.SERVER_TAG
import com.dev.briefing.util.preference.AuthPreferenceHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScrapViewModel(private val repository: ScrapRepository, private val authPreferenceHelper: AuthPreferenceHelper) : ViewModel() {

    private val _scrap: MutableStateFlow<List<Scrap>> =
        MutableStateFlow(listOf())
    val scrap: StateFlow<List<Scrap>>
        get() = _scrap

    val memberId: Int = authPreferenceHelper.getMemberId()

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
                response.let { scrapList ->
                    val tmpScrapList: MutableList<Scrap> = mutableListOf()
                    scrapList.forEach { scrap ->
                        tmpScrapList.add(scrap)
                    }
                    _scrap.value = tmpScrapList
                }
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
            }
        }
    }


}