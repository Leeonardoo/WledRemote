package br.com.leonardo.wledremote.ui.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.leonardo.wledremote.ui.activity.SetupActivity
import br.com.leonardo.wledremote.util.SharedPrefsUtil

class SetupActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(application)

    fun isSetup(): Boolean {
        return sharedPrefsUtil.isIPConfigured()
    }
}