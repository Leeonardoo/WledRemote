package br.com.leonardo.wledremote.ui.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.leonardo.wledremote.model.info.Info
import br.com.leonardo.wledremote.repository.InfoRepository
import br.com.leonardo.wledremote.rest.api.LocalResultWrapper
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SetupViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(application)
    private val infoRepository = InfoRepository()

    private val infoResponse = infoRepository.infoResponse
    private val _info = MutableLiveData<Info>()
    val info: LiveData<Info> = _info

    init {
        flowInfo()
    }

    fun getDeviceInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { infoRepository.getInfo() }
        }
    }

    private fun flowInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                infoResponse.collect {
                    when (it) {
                        is LocalResultWrapper.Success -> _info.postValue(it.value)

                        //handle other status
                    }
                }
            }
        }
    }
}