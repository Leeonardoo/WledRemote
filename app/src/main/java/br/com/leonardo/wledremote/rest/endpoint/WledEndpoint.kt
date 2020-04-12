package br.com.leonardo.wledremote.rest.endpoint

import br.com.leonardo.wledremote.model.State
import br.com.leonardo.wledremote.rest.response.JsonResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface WledEndpoint {

    @POST("/json/state")
    suspend fun setState(@Body state: State): JsonResponse

}