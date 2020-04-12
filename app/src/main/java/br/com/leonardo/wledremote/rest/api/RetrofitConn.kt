package br.com.leonardo.wledremote.rest.api

import br.com.leonardo.wledremote.rest.endpoint.WledEndpoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitConn {

    private var retrofit: Retrofit
    private var httpClient: OkHttpClient
    private val URL_BASE = "http://192.168.1.112"
    private val servicesPool: MutableMap<String, Any?> = HashMap()

    init {
        // Logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor();
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // Add the interceptor to OkHttpClient
        httpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor).build()

        // Build the retrofit object
        retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }

    fun <T> create(service: Class<T>): Any? {
        val key = service.simpleName
        if (!servicesPool.containsKey(key)) {
            servicesPool[key] = retrofit.create(service)
        }
        return servicesPool[key]
    }

    fun wledEndpoint(): WledEndpoint {
        return create(WledEndpoint::class.java) as WledEndpoint
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