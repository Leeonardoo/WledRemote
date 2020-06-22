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

    fun isIPConfigured(prefKey: String = PREF_KEY_IS_CONFIGURED): Boolean =
        getSharedPrefBoolean(prefKey, false)

    fun getSavedIP(prefKey: String = PREF_KEY_DEVICE): String? =
        getSharedPref(prefKey)

    fun setConfigIP(prefKey: String = PREF_KEY_DEVICE, ip: String) {
        sharedPrefs.edit().putString(prefKey, ip).apply()
        sharedPrefs.edit().putBoolean(PREF_KEY_IS_CONFIGURED, true).apply()
    }


    private fun getSharedPref(prefKey: String): String? = sharedPrefs.getString(prefKey, "")

    private fun getSharedPrefBoolean(prefKey: String, defValue: Boolean): Boolean {
        if (prefKey == PREF_KEY_IS_CONFIGURED) {
            return sharedPrefs.getBoolean(prefKey, defValue)
        }
        return defValue
    }
}
