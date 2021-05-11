package br.com.leonardo.wledremote.ui.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.WledApplication
import br.com.leonardo.wledremote.model.info.Info
import br.com.leonardo.wledremote.repository.InfoRepository
import br.com.leonardo.wledremote.rest.api.LocalResultWrapper
import br.com.leonardo.wledremote.util.ActionLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class SetupViewModel(application: Application) : AndroidViewModel(application) {
    private val infoRepository = InfoRepository()

    private val _info = MutableLiveData<Info>()
    val info: LiveData<Info> = _info

    private val _loadingInfo = MutableLiveData(false)
    val loadingInfo: LiveData<Boolean> = _loadingInfo

    private val _infoError = ActionLiveData<String>()
    val infoError: LiveData<String> = _infoError

    fun getInfo() {
        viewModelScope.launch {
            infoRepository.getInfo().collect {
                _loadingInfo.postValue(it == LocalResultWrapper.Loading)

                when (it) {
                    is LocalResultWrapper.Loading -> {
                    }

                    is LocalResultWrapper.Success -> _info.postValue(it.value!!)

                    is LocalResultWrapper.GenericError -> withContext(Dispatchers.Main) {
                        _infoError.sendAction(
                            WledApplication.getAppContext()
                                .getString(R.string.info_setup_unknown_error)
                        )
                    }

                    is LocalResultWrapper.NetworkError -> withContext(Dispatchers.Main) {
                        _infoError.sendAction(
                            WledApplication.getAppContext()
                                .getString(R.string.info_setup_network_error)
                        )
                    }
                }
            }
        }
    }
}