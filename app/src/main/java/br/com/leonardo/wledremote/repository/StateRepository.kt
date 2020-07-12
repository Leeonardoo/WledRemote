package br.com.leonardo.wledremote.repository

import android.util.Log
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.WledApplication
import br.com.leonardo.wledremote.model.state.State
import br.com.leonardo.wledremote.rest.api.ApiHandler
import br.com.leonardo.wledremote.rest.api.LocalResultWrapper
import br.com.leonardo.wledremote.rest.api.ResultWrapper
import br.com.leonardo.wledremote.rest.api.RetrofitConn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class StateRepository {
    private val apiHandler = ApiHandler()
    private val tag = ::InfoRepository.name

    fun sendState(state: State): Flow<LocalResultWrapper<State>> {
        return flow {
            withContext(Dispatchers.IO) {
                val response = apiHandler.handle(this) {
                    RetrofitConn.getInstance().stateEndpoint().sendState(state)
                }

                when (response) {
                    is ResultWrapper.NetworkError -> {
                        Log.e(tag, "Network Error while sending state!")
                        emit(
                            LocalResultWrapper.NetworkError(
                                WledApplication.getAppContext().getString(
                                    R.string.send_state_network_error
                                )
                            )
                        )
                    }

                    is ResultWrapper.GenericError -> {
                        Log.e(
                            tag, "Error while sending state! ${response.error.toString()}"
                        )
                        emit(
                            LocalResultWrapper.GenericError(
                                WledApplication.getAppContext()
                                    .getString(R.string.send_state_generic_error)
                            )
                        )
                    }

                    is ResultWrapper.Success -> {
                        Log.d(tag, "State sent successfully!")
                        emit(LocalResultWrapper.Success(response.value))
                        getState()
                    }
                }
            }
        }.flowOn(Dispatchers.IO).onStart { emit(LocalResultWrapper.Loading) }
    }

    fun getState(): Flow<LocalResultWrapper<State>> {
        return flow {
            withContext(Dispatchers.IO) {
                val response = apiHandler.handle(this) {
                    RetrofitConn.getInstance().stateEndpoint().getState()
                }

                //TODO add error messages
                when (response) {
                    is ResultWrapper.NetworkError -> {
                        Log.e(tag, "Network Error while getting state!")
                        emit(
                            LocalResultWrapper.NetworkError(
                                WledApplication.getAppContext()
                                    .getString(R.string.data_network_error)
                            )
                        )
                    }

                    is ResultWrapper.GenericError -> {
                        Log.e(
                            tag, "Error while getting state! ${response.error.toString()}"
                        )
                        emit(
                            LocalResultWrapper.GenericError(
                                WledApplication.getAppContext()
                                    .getString(R.string.data_generic_error)
                            )
                        )
                    }

                    is ResultWrapper.Success -> {
                        Log.d(tag, "Getting state was successful!")
                        emit(LocalResultWrapper.Success(response.value))
                    }
                }
            }
        }.flowOn(Dispatchers.IO).onStart { emit(LocalResultWrapper.Loading) }
    }
}