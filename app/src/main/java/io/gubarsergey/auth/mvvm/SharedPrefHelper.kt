package io.gubarsergey.auth.mvvm

import android.content.Context
import androidx.core.content.edit
import timber.log.Timber

interface PrefHelper {
    fun saveToken(token: String)
    fun getToken(): String?
}

class SharedPrefHelper(private val context: Context) : PrefHelper {

    companion object {
        private const val TOKEN_KEY = "TOKEN_KEY"
    }

    private val preferences = context.getSharedPreferences("ReduxPrefs", Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        preferences.edit {
            putString(TOKEN_KEY, token)
        }
    }

    override fun getToken(): String? {
        val token = preferences.getString(TOKEN_KEY, null)
        if (token == null) {
            Timber.w("Token is null")
        }
        return token
    }
}
