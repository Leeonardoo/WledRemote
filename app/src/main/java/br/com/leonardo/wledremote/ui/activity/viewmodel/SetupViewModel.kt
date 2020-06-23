package br.com.leonardo.wledremote.ui.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.leonardo.wledremote.repository.InfoRepository
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SetupViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(application)
    private val infoRepository = InfoRepository()

    val infoResponse = infoRepository.infoResponse

    fun getDeviceInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { infoRepository.getInfo() }
        }
    }
}