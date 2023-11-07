package com.dev.briefing.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dev.briefing.util.DailyAlertManager
import com.dev.briefing.util.preference.DailyAlertTimePreferenceHelper

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("BootCompletedReceiver", "in")
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
//            DailyAlertTimePreferenceHelper(context).getAlarmTime().also {
//                DailyAlertManager(context).setDailyAlarm(it.hour, it.minute)
//            }
//            Log.d("BootCompletedReceiver", "success")
        }
    }
}