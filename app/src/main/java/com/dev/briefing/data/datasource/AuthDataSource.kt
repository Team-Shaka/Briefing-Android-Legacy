package com.dev.briefing.data.datasource

import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse

interface AuthDataSource {
    suspend fun getLoginCode(identityToken:GoogleRequest): CommonResponse<GoogleSocialResponse>

}