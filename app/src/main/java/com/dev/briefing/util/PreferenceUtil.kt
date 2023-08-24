package com.dev.briefing.util


import android.content.Context
import android.content.SharedPreferences
import com.dev.briefing.data.NewsDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json
class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)
}


object SharedPreferenceHelper {
    private const val PREF_NAME = "my_shared_pref"
    private const val KEY_ITEMS = "items"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveItems(context: Context, items: List<NewsDetail>) {
        val json = Gson().toJson(items)
        getSharedPreferences(context).edit().putString(KEY_ITEMS, json).apply()
    }

    fun getItems(context: Context): List<NewsDetail> {
        val json = getSharedPreferences(context).getString(KEY_ITEMS, null)
        return if (json != null) {
            val itemType = object : TypeToken<List<NewsDetail>>() {}.type
            Gson().fromJson(json, itemType)
        } else {
            emptyList()
        }
    }
}