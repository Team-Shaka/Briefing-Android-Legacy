package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest

class AuthRepositoryImpl(private val authDataSource: AuthDataSource): AuthRepository {
    override suspend fun getLoginToken(idToken: GoogleRequest): CommonResponse {
        return authDataSource.getLoginCode(idToken)
    }
}