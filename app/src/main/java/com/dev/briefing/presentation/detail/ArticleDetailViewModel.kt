package com.dev.briefing.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.util.SERVER_TAG
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

}