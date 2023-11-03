package com.dev.briefing.data.respository

import android.media.session.MediaSession.Token
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.SingoutResponse
import com.dev.briefing.data.model.TokenRequest

interface AuthRepository {
    suspend fun getLoginToken(idToken: GoogleRequest): CommonResponse<GoogleSocialResponse>
    suspend fun signOut(memberId: Int): CommonResponse<SingoutResponse>
    suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<GoogleSocialResponse>


}