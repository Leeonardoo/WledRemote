package br.com.leonardo.wledremote.rest.api

sealed class LocalResultWrapper<out T> {
    object Loading : LocalResultWrapper<Nothing>()
    data class Success<out T>(val value: T) : LocalResultWrapper<T>()
    data class GenericError(val error: String) : LocalResultWrapper<Nothing>()
    data class NetworkError(val error: String) : LocalResultWrapper<Nothing>()
}