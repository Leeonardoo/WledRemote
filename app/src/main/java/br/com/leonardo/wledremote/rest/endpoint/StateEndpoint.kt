package br.com.leonardo.wledremote.rest.endpoint

import br.com.leonardo.wledremote.model.state.State
import br.com.leonardo.wledremote.rest.request.state.StateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StateEndpoint {

    @GET("/json/state")
    suspend fun getState(): State

    @POST("/json/state")
    suspend fun sendState(
        @Body state: StateRequest
    ): State

}