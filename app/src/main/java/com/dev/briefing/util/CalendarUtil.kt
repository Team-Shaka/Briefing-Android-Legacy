package com.dev.briefing.util

import java.util.Calendar

object CalendarUtil {
    fun getNextTimeInMillisFromNow(hour: Int, minute: Int): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        if (System.currentTimeMillis() > calendar.timeInMillis) {
            calendar.add(Calendar.DATE, 1)
        }

        return calendar.timeInMillis
    }
}