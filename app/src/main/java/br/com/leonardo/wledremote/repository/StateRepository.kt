package br.com.leonardo.wledremote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.leonardo.wledremote.model.state.State
import br.com.leonardo.wledremote.rest.api.ApiHandler
import br.com.leonardo.wledremote.rest.api.ResultWrapper
import br.com.leonardo.wledremote.rest.api.RetrofitConn
import br.com.leonardo.wledremote.rest.request.state.StateRequest
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

    private val _sendStateStatus = MutableLiveData<StateStatus>()
    val sendStateStatus: LiveData<StateStatus> = _sendStateStatus

    private val _stateResponseStatus = MutableLiveData<StateStatus>()
    val stateResponseStatus: LiveData<StateStatus> = _stateResponseStatus

    suspend fun sendState(state: StateRequest) {
        withContext(Dispatchers.IO) {
            _sendStateStatus.postValue(StateStatus.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().stateEndpoint().sendState(state)
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e("StateRepository", "Network Error while sending state!")
                    _sendStateStatus.postValue(StateStatus.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(
                        "StateRepository",
                        "Generic Error while sending state! ${response.error.toString()}"
                    )
                    _sendStateStatus.postValue(StateStatus.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d("StateRepository", "State sent successfully!")
                    _sendStateStatus.postValue(StateStatus.Success(response.value))
                    getState()
                }
            }
        }
    }

    suspend fun getState() {
        withContext(Dispatchers.IO) {
            _stateResponseStatus.postValue(StateStatus.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().stateEndpoint().getState()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e("StateRepository", "Network Error while getting state!")
                    _stateResponseStatus.postValue(StateStatus.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(
                        "StateRepository",
                        "Generic Error while getting state! ${response.error.toString()}"
                    )
                    _stateResponseStatus.postValue(StateStatus.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d("StateRepository", "Getting state was successful!")
                    _stateResponseStatus.postValue(StateStatus.Success(response.value))
                }
            }
        }
    }
}