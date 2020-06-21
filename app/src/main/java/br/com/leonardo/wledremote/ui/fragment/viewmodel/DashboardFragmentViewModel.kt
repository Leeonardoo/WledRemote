package br.com.leonardo.wledremote.ui.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil

class DashboardFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(application)

}