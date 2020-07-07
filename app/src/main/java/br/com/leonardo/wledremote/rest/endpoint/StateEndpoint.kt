package br.com.leonardo.wledremote.rest.endpoint

import br.com.leonardo.wledremote.model.state.State
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StateEndpoint {

    @GET("/json/state")
    suspend fun getState(): State

    @POST("/json/state")
    suspend fun sendState(
        @Body state: State
    ): State
}