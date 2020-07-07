package br.com.leonardo.wledremote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.leonardo.wledremote.model.state.State
import br.com.leonardo.wledremote.rest.api.ApiHandler
import br.com.leonardo.wledremote.rest.api.ResultWrapper
import br.com.leonardo.wledremote.rest.api.RetrofitConn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class StateStatus {
    object Loading : StateStatus()
    data class GenericError(val error: String) : StateStatus()
    data class NetworkError(val error: String) : StateStatus()
    data class Success(val state: State) : StateStatus()
}

class StateRepository {
    private val apiHandler = ApiHandler()

    private val _sendStateResponse = MutableLiveData<StateStatus>()
    val sendStateResponse: LiveData<StateStatus> = _sendStateResponse

    private val _stateResponse = MutableLiveData<StateStatus>()
    val stateResponse: LiveData<StateStatus> = _stateResponse

    suspend fun sendState(state: State) {
        withContext(Dispatchers.IO) {
            _sendStateResponse.postValue(StateStatus.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().stateEndpoint().sendState(state)
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e("StateRepository", "Network Error while sending state!")
                    _sendStateResponse.postValue(StateStatus.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(
                        "StateRepository",
                        "Generic Error while sending state! ${response.error.toString()}"
                    )
                    _sendStateResponse.postValue(StateStatus.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d("StateRepository", "State sent successfully!")
                    _sendStateResponse.postValue(StateStatus.Success(response.value))
                    getState()
                }
            }
        }
    }

    suspend fun getState() {
        withContext(Dispatchers.IO) {
            _stateResponse.postValue(StateStatus.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().stateEndpoint().getState()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e("StateRepository", "Network Error while getting state!")
                    _stateResponse.postValue(StateStatus.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(
                        "StateRepository",
                        "Generic Error while getting state! ${response.error.toString()}"
                    )
                    _stateResponse.postValue(StateStatus.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d("StateRepository", "Getting state was successful!")
                    _stateResponse.postValue(StateStatus.Success(response.value))
                }
            }
        }
    }
}