package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.ScrapDataSource
import com.dev.briefing.model.Scrap
import com.dev.briefing.model.toScrap

class ScrapRepositoryImpl(private val scrapDataSource: ScrapDataSource) : ScrapRepository {
    override suspend fun getScrap(memberId: Int): List<Scrap> {
        val tmpList = mutableListOf<Scrap>()
        scrapDataSource.getScrap(memberId).result.forEach {
            tmpList.add(it.toScrap())
        }
        return tmpList
    }
}