package br.com.leonardo.wledremote.repository

import android.util.Log
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.WledApplication
import br.com.leonardo.wledremote.model.info.Info
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
class InfoRepository {
    private val apiHandler = ApiHandler()
    private val tag = ::InfoRepository.name

    fun getInfo(): Flow<LocalResultWrapper<Info>> {
        return flow {
            withContext(Dispatchers.IO) {
                val response = apiHandler.handle(this) {
                    RetrofitConn.getInstance().infoEndpoint().getInfo()
                }

                when (response) {
                    is ResultWrapper.NetworkError -> {
                        Log.e(tag, "Network Error while getting info!")
                        emit(
                            LocalResultWrapper.NetworkError(
                                WledApplication.getAppContext()
                                    .getString(R.string.info_network_error)
                            )
                        )
                    }

                    is ResultWrapper.GenericError -> {
                        Log.e(tag, "Generic Error while getting info! ${response.error.toString()}")
                        emit(
                            LocalResultWrapper.GenericError(
                                WledApplication.getAppContext()
                                    .getString(R.string.info_generic_error)
                            )
                        )
                    }

                    is ResultWrapper.Success -> {
                        Log.d(tag, "Got info successfully!")
                        emit(LocalResultWrapper.Success(response.value))
                    }
                }
            }
        }.flowOn(Dispatchers.IO).onStart { emit(LocalResultWrapper.Loading) }
    }

    fun getEffects(): Flow<LocalResultWrapper<List<String>>> {
        return flow {
            withContext(Dispatchers.IO) {
                val response = apiHandler.handle(this) {
                    RetrofitConn.getInstance().infoEndpoint().getEffects()
                }

                //TODO add error messages
                when (response) {
                    is ResultWrapper.NetworkError -> {
                        Log.e(tag, "Network Error while getting effects!")
                        emit(
                            LocalResultWrapper.NetworkError(
                                WledApplication.getAppContext()
                                    .getString(R.string.effects_network_error)
                            )
                        )
                    }

                    is ResultWrapper.GenericError -> {
                        Log.e(
                            tag,
                            "Error while getting effects! ${response.error.toString()}"
                        )
                        emit(
                            LocalResultWrapper.GenericError(
                                WledApplication.getAppContext()
                                    .getString(R.string.effects_unknown_error)
                            )
                        )
                    }

                    is ResultWrapper.Success -> {
                        Log.d(tag, "Got effects successfully!")
                        emit(LocalResultWrapper.Success(response.value))
                    }
                }
            }
        }.flowOn(Dispatchers.IO).onStart { emit(LocalResultWrapper.Loading) }
    }

    fun getPalettes(): Flow<LocalResultWrapper<List<String>>> {
        return flow {
            withContext(Dispatchers.IO) {
                val response = apiHandler.handle(this) {
                    RetrofitConn.getInstance().infoEndpoint().getPalettes()
                }

                when (response) {
                    is ResultWrapper.NetworkError -> {
                        Log.e(tag, "Network Error while getting palettes!")
                        emit(
                            LocalResultWrapper.NetworkError(
                                WledApplication.getAppContext()
                                    .getString(R.string.palettes_network_error)
                            )
                        )
                    }

                    is ResultWrapper.GenericError -> {
                        Log.e(
                            tag, "Error while getting palettes! ${response.error.toString()}"
                        )
                        emit(
                            LocalResultWrapper.GenericError(
                                WledApplication.getAppContext()
                                    .getString(R.string.palettes_generic_error)
                            )
                        )
                    }

                    is ResultWrapper.Success -> {
                        Log.d(tag, "Got palettes successfully!")
                        emit(LocalResultWrapper.Success(response.value))
                    }
                }
            }
        }.flowOn(Dispatchers.IO).onStart { emit(LocalResultWrapper.Loading) }
    }
}