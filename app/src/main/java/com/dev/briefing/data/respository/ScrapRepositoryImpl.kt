package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.ScrapDataSource
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse
import com.dev.briefing.model.Scrap
import com.dev.briefing.model.toScrap

class ScrapRepositoryImpl(private val scrapDataSource: ScrapDataSource): ScrapRepository {
    override suspend fun getScrap(memberId: Int): List<Scrap> {
        val tmpList = mutableListOf<Scrap>()
        scrapDataSource.getScrap(memberId).result.forEach {
            tmpList.add(it.toScrap())
        }
        return tmpList
    }

    override suspend fun setScrap(
        memberId: Int,
        articleId: Long
    ): CommonResponse<SetScrapResponse> {
        return scrapDataSource.setScrap(memberId, articleId)
    }

    override suspend fun unScrap(memberId: Int, articleId: Long): CommonResponse<UnScrapResponse> {
        return scrapDataSource.unScrap(memberId, articleId)

    }
}