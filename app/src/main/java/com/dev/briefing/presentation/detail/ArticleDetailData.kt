package com.dev.briefing.presentation.detail

import com.dev.briefing.data.model.Article
import com.dev.briefing.data.model.BriefingDetailResponse
import android.util.Log
import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.model.BriefingPreview
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.network.RetrofitClient
import com.dev.briefing.util.SERVER_TAG
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getArticleDetail(
    id: Int = 1,
): BriefingDetailResponse {
    var articleDetail: BriefingDetailResponse = BriefingDetailResponse(
        id = id, rank = 1, title = "test",
        subtitle = "llblblb", content = "fdsfsdddddddddddddddddd", listOf(
            Article(
                id = 1,
                press = "fdfs",
                title = "title13",
                url = "https://url13.com"
            )
        )
    )
    val briefingApi: BriefingApi? = RetrofitClient.getClient()?.create(
        BriefingApi::class.java
    )

    val call = briefingApi?.getBriefingDetail(
        id = id
    )

    call?.enqueue(object : Callback<BriefingDetailResponse> {
        override fun onResponse(
            call: Call<BriefingDetailResponse>, response: Response<BriefingDetailResponse>
        ) {
            if (response.isSuccessful) {
                Log.d(SERVER_TAG, "Success to get article data. Code: ${response.body()}")
                articleDetail = response.body()!!
            } else {
                Log.d(SERVER_TAG, "Failed to get article data. Code: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<BriefingDetailResponse>, t: Throwable) {
            Log.d(SERVER_TAG, "Failed to get article data. Code: ${t.message}")

        }
    })
    Log.d(SERVER_TAG, "before Return${articleDetail.id}")
    return articleDetail
}

