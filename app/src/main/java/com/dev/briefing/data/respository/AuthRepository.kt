package com.dev.briefing.data.respository

import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest

interface AuthRepository {
    suspend fun getLoginToken(idToken:GoogleRequest): CommonResponse

}