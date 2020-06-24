package br.com.leonardo.wledremote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.leonardo.wledremote.model.info.Info
import br.com.leonardo.wledremote.rest.api.ApiHandler
import br.com.leonardo.wledremote.rest.api.ResultWrapper
import br.com.leonardo.wledremote.rest.api.RetrofitConn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class InfoStatus {
    object Loading : InfoStatus()
    data class GenericError(val error: String) : InfoStatus()
    data class NetworkError(val error: String) : InfoStatus()
    data class Success(val info: Info) : InfoStatus()
}

sealed class EffectStatus {
    object Loading : EffectStatus()
    data class GenericError(val error: String) : EffectStatus()
    data class NetworkError(val error: String) : EffectStatus()
    data class Success(val effects: List<String>) : EffectStatus()
}

sealed class PaletteStatus {
    object Loading : PaletteStatus()
    data class GenericError(val error: String) : PaletteStatus()
    data class NetworkError(val error: String) : PaletteStatus()
    data class Success(val palettes: List<String>) : PaletteStatus()
}

class InfoRepository {
    private val apiHandler = ApiHandler()
    private val tag = ::InfoRepository.name

    private val _infoResponse = MutableLiveData<InfoStatus>()
    val infoResponse: LiveData<InfoStatus> = _infoResponse

    private val _effectResponse = MutableLiveData<EffectStatus>()
    val effectResponse: LiveData<EffectStatus> = _effectResponse

    private val _paletteResponse = MutableLiveData<PaletteStatus>()
    val paletteResponse: LiveData<PaletteStatus> = _paletteResponse

    suspend fun getInfo() {
        withContext(Dispatchers.IO) {
            _infoResponse.postValue(InfoStatus.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().infoEndpoint().getInfo()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e(tag, "Network Error while getting info!")
                    _infoResponse.postValue(InfoStatus.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(tag, "Generic Error while getting info! ${response.error.toString()}")
                    _infoResponse.postValue(InfoStatus.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d(tag, "Got info successfully!")
                    _infoResponse.postValue(InfoStatus.Success(response.value))
                }
            }
        }
    }

    suspend fun getEffects() {
        withContext(Dispatchers.IO) {
            _effectResponse.postValue(EffectStatus.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().infoEndpoint().getEffects()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e(tag, "Network Error while getting effects!")
                    _effectResponse.postValue(EffectStatus.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(tag, "Generic Error while getting effects! ${response.error.toString()}")
                    _effectResponse.postValue(EffectStatus.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d(tag, "Got effects successfully!")
                    _effectResponse.postValue(EffectStatus.Success(response.value))
                }
            }
        }
    }

    suspend fun getPalettes() {
        withContext(Dispatchers.IO) {
            _paletteResponse.postValue(PaletteStatus.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().infoEndpoint().getPalettes()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e(tag, "Network Error while getting palettes!")
                    _paletteResponse.postValue(PaletteStatus.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(tag, "Generic Error while getting palettes! ${response.error.toString()}")
                    _paletteResponse.postValue(PaletteStatus.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d(tag, "Got palettes successfully!")
                    _paletteResponse.postValue(PaletteStatus.Success(response.value))
                }
            }
        }
    }
}