package com.dev.briefing.data.respository

import com.dev.briefing.model.Scrap

interface ScrapRepository {

    suspend fun getScrap(memberId: Int): List<Scrap>

}