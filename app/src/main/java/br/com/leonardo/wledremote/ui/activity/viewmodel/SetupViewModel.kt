package br.com.leonardo.wledremote.ui.activity.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.model.info.Info
import br.com.leonardo.wledremote.repository.InfoRepository
import br.com.leonardo.wledremote.rest.api.LocalResultWrapper
import br.com.leonardo.wledremote.util.ActionLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context
) : ViewModel() {
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

                    is LocalResultWrapper.Success -> it.value.let { newInfo ->
                        _info.postValue(newInfo)
                    }

                    is LocalResultWrapper.GenericError -> withContext(Dispatchers.Main) {
                        _infoError.sendAction(
                            appContext.getString(R.string.info_setup_unknown_error)
                        )
                    }

                    is LocalResultWrapper.NetworkError -> withContext(Dispatchers.Main) {
                        _infoError.sendAction(
                            appContext.getString(R.string.info_setup_network_error)
                        )
                    }
                }
            }
        }
    }
}