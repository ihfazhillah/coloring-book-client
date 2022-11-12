package com.ihfazh.warnain.common

import android.content.Context
import androidx.core.content.edit
import com.ihfazh.warnain.R
import org.koin.core.annotation.Single
import java.util.UUID

@Single
class PreferenceManager(context: Context) {
    companion object {
        private const val DEVICE_GUID = "device_guid"
        private const val USER_TOKEN = "user_token"
        private const val ENDPOINT = "server_endpoint"
    }

    private val prefs = context.getSharedPreferences(
        context.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )


    fun getToken(): String? =
        prefs.getString(USER_TOKEN, null)

    fun setToken(token: String?) {
        prefs.edit {
            putString(USER_TOKEN, token)
            apply()
        }
    }

    fun getServerEndpoint(): String? =
        prefs.getString(ENDPOINT, null)

    fun setServerEndpoint(value: String?) {
        prefs.edit {
            putString(ENDPOINT, value)
            apply()
        }
    }

    fun getGUID(): String {
        var guid = prefs.getString(DEVICE_GUID, null)
        if (guid == null){
            guid = UUID.randomUUID().toString()
            prefs.edit {
                putString(DEVICE_GUID, guid)
            }
        }
        return guid
    }
}