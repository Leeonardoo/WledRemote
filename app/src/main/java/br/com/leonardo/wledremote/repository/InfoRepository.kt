package br.com.leonardo.wledremote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import br.com.leonardo.wledremote.model.info.Info
import br.com.leonardo.wledremote.rest.api.ApiHandler
import br.com.leonardo.wledremote.rest.api.LocalResultWrapper
import br.com.leonardo.wledremote.rest.api.ResultWrapper
import br.com.leonardo.wledremote.rest.api.RetrofitConn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class InfoRepository {
    private val apiHandler = ApiHandler()
    private val tag = ::InfoRepository.name

    private val _infoResponse = MutableLiveData<LocalResultWrapper<Info>>()
    val infoResponse: Flow<LocalResultWrapper<Info>> = _infoResponse.asFlow()

    private val _effectResponse = MutableLiveData<LocalResultWrapper<List<String>>>()
    val effectResponse: LiveData<LocalResultWrapper<List<String>>> = _effectResponse

    private val _paletteResponse = MutableLiveData<LocalResultWrapper<List<String>>>()
    val paletteResponse: LiveData<LocalResultWrapper<List<String>>> = _paletteResponse

    suspend fun getInfo() {
        withContext(Dispatchers.IO) {
            _infoResponse.postValue(LocalResultWrapper.Loading)
            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().infoEndpoint().getInfo()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e(tag, "Network Error while getting info!")
                    _infoResponse.postValue(LocalResultWrapper.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(tag, "Generic Error while getting info! ${response.error.toString()}")
                    _infoResponse.postValue(LocalResultWrapper.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d(tag, "Got info successfully!")
                    _infoResponse.postValue(LocalResultWrapper.Success(response.value))
                }
            }
        }
    }

    suspend fun getEffects() {
        withContext(Dispatchers.IO) {
            _effectResponse.postValue(LocalResultWrapper.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().infoEndpoint().getEffects()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e(tag, "Network Error while getting effects!")
                    _effectResponse.postValue(LocalResultWrapper.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(tag, "Generic Error while getting effects! ${response.error.toString()}")
                    _effectResponse.postValue(LocalResultWrapper.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d(tag, "Got effects successfully!")
                    _effectResponse.postValue(LocalResultWrapper.Success(response.value))
                }
            }
        }
    }

    suspend fun getPalettes() {
        withContext(Dispatchers.IO) {
            _paletteResponse.postValue(LocalResultWrapper.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().infoEndpoint().getPalettes()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e(tag, "Network Error while getting palettes!")
                    _paletteResponse.postValue(LocalResultWrapper.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(tag, "Generic Error while getting palettes! ${response.error.toString()}")
                    _paletteResponse.postValue(LocalResultWrapper.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d(tag, "Got palettes successfully!")
                    _paletteResponse.postValue(LocalResultWrapper.Success(response.value))
                }
            }
        }
    }
}