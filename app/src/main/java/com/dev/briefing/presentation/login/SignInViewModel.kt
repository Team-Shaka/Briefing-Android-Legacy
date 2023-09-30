package com.dev.briefing.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.respository.AuthRepository
import com.dev.briefing.util.SERVER_TAG
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SignInViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _accessToken: MutableLiveData<String> = MutableLiveData<String>()
    val accessToken: LiveData<String>
        get() = _accessToken

    private val _statusMsg: MutableLiveData<String> = MutableLiveData<String>("server failed")
    val statusMsg: LiveData<String>
        get() = _statusMsg
    fun getLoginCode(idToken: String):Boolean {
        var result = false
        viewModelScope.launch {
            try {
                val response = repository.getLoginToken(
                    GoogleRequest(
                        identityToken = idToken
                    )
                )
                _accessToken.value = response.result.accessToken
                _statusMsg.value = response.message
                result = true
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
                _statusMsg.value = e.toString()
                result = false
            }
        }
        return result
    }

}
