package br.com.leonardo.wledremote.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPrefsUtil private constructor(context: Context) {

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var INSTANCE: SharedPrefsUtil? = null

        fun getInstance(context: Context): SharedPrefsUtil =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: sharedPreferences(context).also { INSTANCE = it }
            }

        private fun sharedPreferences(context: Context): SharedPrefsUtil {
            Log.v("SINGLETON", "SharedPrefsUtil foi instanciado!")
            return SharedPrefsUtil(context.applicationContext)
        }
    }

    fun isIPConfigured(): Boolean =
        getSharedPrefBoolean(PREF_KEY_IS_CONFIGURED, false)

    fun getSavedIP(): String? =
        getSharedPref(PREF_KEY_DEVICE)

    fun setConfigIP(ip: String) =
        sharedPrefs.edit().putString(PREF_KEY_DEVICE, ip).apply()

    fun setIsIPConfigured(value: Boolean) =
        sharedPrefs.edit().putBoolean(PREF_KEY_IS_CONFIGURED, value).apply()

    private fun getSharedPref(prefKey: String): String? = sharedPrefs.getString(prefKey, "")

    private fun getSharedPrefBoolean(prefKey: String, defValue: Boolean): Boolean {
        if (prefKey == PREF_KEY_IS_CONFIGURED) {
            return sharedPrefs.getBoolean(prefKey, defValue)
        }
        return defValue
    }
}
