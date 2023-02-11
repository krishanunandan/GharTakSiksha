package com.ghartakshiksha.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT = "HomeBakers"

class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    fun saveLastSavedAt(savedAt: String) {
        preference.edit().putString(
            KEY_SAVED_AT,
            savedAt
        ).apply()
    }

    fun saveIntToSF(keyID: String, value: Int) {
        preference.edit().putInt(
            keyID,
            value
        ).apply()
    }

    fun saveBooleanToSF(keyID: String, value: Boolean) {
        preference.edit().putBoolean(
            keyID,
            value
        ).apply()
    }

    fun getBooleanToSF(keyID: String): Boolean {
        return preference.getBoolean(keyID, false)
    }

    fun getIntToSF(keyID: String): Int {
        return preference.getInt(keyID, 0)
    }

    fun savedInPreference(keyID: String, value: String) {
        preference.edit().putString(
            keyID,
            value
        ).apply()
    }

    fun getValueByPreference(keyID: String): String? {
        return preference.getString(keyID, "")
    }

    fun getLastSavedAt(): String? {
        return preference.getString(KEY_SAVED_AT, null)
    }

    fun clearPreference(){
        preference.edit().clear().apply()
    }


}