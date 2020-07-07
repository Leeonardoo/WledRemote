package br.com.leonardo.wledremote.model.info

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wifi(

    @Json(name = "rssi")
    val rssi: Int? = null,

    @Json(name = "bssid")
    val bssid: String? = null,

    @Json(name = "channel")
    val channel: Int? = null,

    @Json(name = "signal")
    val signal: Int? = null
)