package br.com.leonardo.wledremote.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil

class SetupActivityViewModel(application: Application) : AndroidViewModel(application) {
    val sharedPrefsUtil = SharedPrefsUtil.getInstance(application)
}