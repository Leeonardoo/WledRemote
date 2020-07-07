package br.com.leonardo.wledremote.model.info

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Leds(

    @Json(name = "pin")
    val pin: List<Int?>? = null,

    @Json(name = "seglock")
    val segLock: Boolean? = null,

    @Json(name = "count")
    val count: Int? = null,

    @Json(name = "maxpwr")
    val maxPower: Int? = null,

    @Json(name = "pwr")
    val currentPowerUsage: Int? = null,

    @Json(name = "maxseg")
    val maxSegments: Int? = null,

    @Json(name = "rgbw")
    val rgbw: Boolean? = null,

    @Json(name = "wv")
    val wv: Boolean? = null
)