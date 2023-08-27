package com.dev.briefing.data.datasource

import com.dev.briefing.data.model.BriefingResponse


interface BriefingDataSource {

    suspend fun getBriefingKeyword(briefingDate:String,type:String): BriefingResponse
}