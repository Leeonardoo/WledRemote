package br.com.leonardo.wledremote.model.state

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Udpn(

    @Json(name = "recv")
    val recv: Boolean? = null,

    val send: Boolean? = null
)