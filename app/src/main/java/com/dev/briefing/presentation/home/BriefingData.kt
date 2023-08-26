package com.dev.briefing.presentation.home

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

fun getBriefingData(
    briefingDate: String = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    type: String = "Korea",
) :BriefingResponse{
    var briefingResponse:BriefingResponse = BriefingResponse(
        created_at = briefingDate,
        briefings = listOf<BriefingPreview>()
    )
    val briefingApi: BriefingApi? = RetrofitClient.getClient()?.create(
        BriefingApi::class.java
    )

    val call = briefingApi?.getBriefingKeyword(
        brefingsDate = briefingDate,
        type = type
    )

    call?.enqueue(object : Callback<BriefingResponse> {
        override fun onResponse(
            call: Call<BriefingResponse>, response: Response<BriefingResponse>
        ) {
            if (response.isSuccessful) {
                briefingResponse = response.body() !!
            } else {
                Log.d(SERVER_TAG,"Failed to get briefing data. Code: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<BriefingResponse>, t: Throwable) {
            Log.d(SERVER_TAG,"Failed to get briefing data. Code: ${t.message}")

        }
    })
    return briefingResponse
}

