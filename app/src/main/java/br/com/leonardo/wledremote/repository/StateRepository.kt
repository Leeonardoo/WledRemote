package br.com.leonardo.wledremote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.leonardo.wledremote.model.state.State
import br.com.leonardo.wledremote.rest.api.ApiHandler
import br.com.leonardo.wledremote.rest.api.LocalResultWrapper
import br.com.leonardo.wledremote.rest.api.ResultWrapper
import br.com.leonardo.wledremote.rest.api.RetrofitConn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StateRepository {
    private val apiHandler = ApiHandler()

    private val _sendStateResponse = MutableLiveData<LocalResultWrapper<State>>()
    val sendStateResponse: LiveData<LocalResultWrapper<State>> = _sendStateResponse

    private val _stateResponse = MutableLiveData<LocalResultWrapper<State>>()
    val stateResponse: LiveData<LocalResultWrapper<State>> = _stateResponse

    suspend fun sendState(state: State) {
        withContext(Dispatchers.IO) {
            _sendStateResponse.postValue(LocalResultWrapper.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().stateEndpoint().sendState(state)
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e("StateRepository", "Network Error while sending state!")
                    _sendStateResponse.postValue(LocalResultWrapper.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(
                        "StateRepository",
                        "Generic Error while sending state! ${response.error.toString()}"
                    )
                    _sendStateResponse.postValue(LocalResultWrapper.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d("StateRepository", "State sent successfully!")
                    _sendStateResponse.postValue(LocalResultWrapper.Success(response.value))
                    getState()
                }
            }
        }
    }

    suspend fun getState() {
        withContext(Dispatchers.IO) {
            _stateResponse.postValue(LocalResultWrapper.Loading)

            val response = apiHandler.handle(this) {
                RetrofitConn.getInstance().stateEndpoint().getState()
            }

            //TODO add error messages
            when (response) {
                is ResultWrapper.NetworkError -> {
                    Log.e("StateRepository", "Network Error while getting state!")
                    _stateResponse.postValue(LocalResultWrapper.NetworkError("blank"))
                }

                is ResultWrapper.GenericError -> {
                    Log.e(
                        "StateRepository",
                        "Generic Error while getting state! ${response.error.toString()}"
                    )
                    _stateResponse.postValue(LocalResultWrapper.GenericError("blank"))
                }

                is ResultWrapper.Success -> {
                    Log.d("StateRepository", "Getting state was successful!")
                    _stateResponse.postValue(LocalResultWrapper.Success(response.value))
                }
            }
        }
    }
}