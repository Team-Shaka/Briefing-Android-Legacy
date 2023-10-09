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
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.data.respository.ScrapRepository
import com.dev.briefing.util.SERVER_TAG
import com.dev.briefing.util.SharedPreferenceHelper
import kotlinx.coroutines.launch

class ArticleDetailViewModel(private val repository: BriefingRepository,private val id: Int):ViewModel() {
    private val _detailPage: MutableLiveData<BriefingDetailResponse> = MutableLiveData<BriefingDetailResponse>()
    val detailPage: LiveData<BriefingDetailResponse>
        get() = _detailPage
    init{
        getBrieingId(id)
    }
    fun getBrieingId(id:Int){
        viewModelScope.launch {
            try {
                val response = repository.getBriefingDetail(
                    id = id)
                if(response != null) {
                    _detailPage.value = response
                } else {
                }
            } catch (e: Throwable) {
                Log.d(SERVER_TAG,e.toString())
            }

            Log.d(SERVER_TAG,"끝!")
        }
    }
    /**
     * 스크랩한 기사들을 가져오는 메소드
     * [_scraList] 업데이트
     */

}