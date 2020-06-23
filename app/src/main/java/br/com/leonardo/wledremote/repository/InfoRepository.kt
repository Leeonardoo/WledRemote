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

class InfoRepository {
    private val apiHandler = ApiHandler()

    private val _infoResponse = MutableLiveData<InfoStatus>()
    val infoResponse: LiveData<InfoStatus> = _infoResponse

    suspend fun getInfo() {
        withContext(Dispatchers.IO) {
            _infoResponse.postValue(InfoStatus.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().infoEndpoint().getInfo()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e("InfoRepository", "Network Error while getting info!")
                    _infoResponse.postValue(InfoStatus.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(
                        "InfoRepository",
                        "Generic Error while getting info! ${response.error.toString()}"
                    )
                    _infoResponse.postValue(InfoStatus.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d("InfoRepository", "Got info successfully!")
                    _infoResponse.postValue(InfoStatus.Success(response.value))
                }
            }
        }
    }
}