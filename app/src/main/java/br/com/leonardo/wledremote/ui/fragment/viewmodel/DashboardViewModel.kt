package br.com.leonardo.wledremote.ui.fragment.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(appContext)
}