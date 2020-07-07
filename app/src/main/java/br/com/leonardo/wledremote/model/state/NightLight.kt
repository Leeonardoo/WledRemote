package br.com.leonardo.wledremote.model.state

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NightLight(

    @Json(name = "dur")
    val duration: Int? = null,

    val fade: Boolean? = null,

    @Json(name = "tbri")
    val brightness: Int? = null,

    val on: Boolean? = null
)