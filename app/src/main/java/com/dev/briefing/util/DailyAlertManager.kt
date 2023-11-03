package com.dev.briefing.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dev.briefing.worker.DailyAlertWorker
import java.time.Duration
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

class DailyAlertManager(
    private val context: Context
) {
    fun setDailyAlarm(hourOfDay: Int, minute: Int) {
        // 현재 시간과 설정할 시간을 계산
        val now = LocalTime.now()
        val targetTime = LocalTime.of(hourOfDay, minute)

        // 초단위로 초기 지연 시간을 계산
        var initialDelayInSeconds = now.until(targetTime, ChronoUnit.SECONDS)

        if (now.isAfter(targetTime)) {
            // 이미 설정 시간이 지났다면 다음 날로 설정
            initialDelayInSeconds += Duration.ofDays(1).seconds
        }

        // 반복 간격도 초단위로 설정
        val repeatIntervalInSeconds = Duration.ofDays(1).seconds

        // WorkRequest 생성
        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyAlertWorker>(
            repeatIntervalInSeconds, TimeUnit.SECONDS
        )
            .setInitialDelay(initialDelayInSeconds, TimeUnit.SECONDS)
            .build()

        // WorkManager에 작업 예약
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_work",
            ExistingPeriodicWorkPolicy.UPDATE,
            dailyWorkRequest
        )
    }
}