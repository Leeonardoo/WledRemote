package br.com.leonardo.wledremote.rest.endpoint

import br.com.leonardo.wledremote.model.info.Info
import retrofit2.http.GET

interface InfoEndpoint {

    @GET("/json/info")
    suspend fun getInfo(): Info

    @GET("/json/effects")
    suspend fun getEffects(): List<String>

    @GET("/json/palettes")
    suspend fun getPalettes(): List<String>
}