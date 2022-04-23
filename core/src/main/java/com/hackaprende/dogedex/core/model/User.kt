package com.hackaprende.dogedex.core.model

import android.app.Activity
import android.content.Context

class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
) {

    companion object {
        private const val AUTH_PREFS = "auth_prefs"
        private const val ID_KEY = "id"
        private const val EMAIL_KEY = "email"
        private const val AUTH_TOKEN_KEY = "auth_token"

        fun setLoggedInUser(activity: Activity, user: User) {
            activity.getSharedPreferences(AUTH_PREFS,
                Context.MODE_PRIVATE).also {
                    it.edit()
                        .putLong(ID_KEY, user.id)
                        .putString(EMAIL_KEY, user.email)
                        .putString(AUTH_TOKEN_KEY, user.authenticationToken)
                        .apply()
            }
        }

        fun getLoggedInUser(activity: Activity): User? {
            val prefs = activity.getSharedPreferences(
                AUTH_PREFS,
                Context.MODE_PRIVATE
            ) ?: return null

            val userId = prefs.getLong(ID_KEY, 0)
            if (userId == 0L) {
                return null
            }

            return User(
                userId,
                prefs.getString(EMAIL_KEY, "") ?: "",
                prefs.getString(AUTH_TOKEN_KEY, "") ?: "",
            )
        }

        fun logout(activity: Activity) {
            activity.getSharedPreferences(
                AUTH_PREFS,
                Context.MODE_PRIVATE
            ).also {
                it.edit().clear().apply()
            }
        }
    }
}