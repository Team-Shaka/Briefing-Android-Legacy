package com.dev.briefing.util.preference

import android.content.Context
import android.content.SharedPreferences
import com.dev.briefing.util.PREF_NAME

class AuthPreferenceHelper(val context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    fun getAccessToken(): String? {
        return prefs.getString(JWT_TOKEN, "")
    }

    fun getRefreshToken(): String? {
        return prefs.getString(REFRESH_TOKEN, "")
    }

    fun getMemberId(): Int {
        return prefs.getInt(MEMBER_ID, -1)
    }

    fun saveMemberId(memberId: Int) {
        editor.putInt(MEMBER_ID, memberId)
        editor.apply()
    }

    fun saveToken(accessToken: String, refreshToken: String) {
        editor.putString(JWT_TOKEN, accessToken)
        editor.putString(REFRESH_TOKEN, refreshToken)
        editor.apply()
    }

    fun clearToken() {
        editor.putString(JWT_TOKEN, "")
        editor.putString(REFRESH_TOKEN, "")
        editor.apply()
    }

    fun clearMemberId() {
        editor.putInt(MEMBER_ID, -1)
        editor.apply()
    }

    companion object {
        const val JWT_TOKEN = "token"
        const val REFRESH_TOKEN = "refresh_token"
        const val MEMBER_ID = "memberId"
    }
}