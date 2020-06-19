package br.com.leonardo.wledremote.rest.api

import br.com.leonardo.wledremote.rest.response.ErrorResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@Suppress("SpellCheckingInspection")
class ApiHandler {

    suspend fun <T> handle(
        coroutineScope: CoroutineScope,
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(coroutineScope.coroutineContext) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}
