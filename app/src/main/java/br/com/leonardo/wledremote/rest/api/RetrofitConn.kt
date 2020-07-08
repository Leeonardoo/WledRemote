package br.com.leonardo.wledremote.rest.api

import br.com.leonardo.wledremote.WledApplication
import br.com.leonardo.wledremote.rest.endpoint.InfoEndpoint
import br.com.leonardo.wledremote.rest.endpoint.StateEndpoint
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class RetrofitConn {

    private var retrofit: Retrofit
    private var httpClient: OkHttpClient
    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(WledApplication.getAppContext())
    private val baseUrl = "http://" + sharedPrefsUtil.getSavedIP()
    private val servicesPool: MutableMap<String, Any?> = HashMap()

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor).build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
    }

    private fun <T> create(service: Class<T>): Any? {
        val key = service.simpleName
        if (!servicesPool.containsKey(key)) {
            servicesPool[key] = retrofit.create(service)
        }
        return servicesPool[key]
    }

    fun infoEndpoint(): InfoEndpoint {
        return create(InfoEndpoint::class.java) as InfoEndpoint
    }

    fun stateEndpoint(): StateEndpoint {
        return create(StateEndpoint::class.java) as StateEndpoint
    }

    companion object {
        @Volatile
        private var INSTANCE: RetrofitConn? = null

        fun getInstance(): RetrofitConn =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RetrofitConn()
            }
    }
}