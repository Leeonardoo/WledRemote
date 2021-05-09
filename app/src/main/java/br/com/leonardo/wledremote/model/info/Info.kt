package br.com.leonardo.wledremote.model.info

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Info(
    val lwip: Int? = null,

    @Json(name = "lip")
    val lip: String? = null,

    @Json(name = "ver")
    val versionName: String? = null,

    val wifi: Wifi? = null,

    @Json(name = "product")
    val productName: String? = null,

    @Json(name = "palcount")
    val palettesCount: Int? = null,

    @Json(name = "mac")
    val macAddress: String? = null,

    @Json(name = "freeheap")
    val freeHeap: Int? = null,

    @Json(name = "uptime")
    val uptime: Long? = null,

    @Json(name = "vid")
    val buildId: Int? = null,

    @Json(name = "str")
    val str: Boolean? = null,

    @Json(name = "core")
    val arduinoVersion: String? = null,

    @Json(name = "opt")
    val opt: Int? = null,

    @Json(name = "fxcount")
    val effectsCount: Int? = null,

    val name: String? = null,

    val leds: Leds? = null,

    @Json(name = "arch")
    val platformName: String? = null,

    val brand: String? = null,

    @Json(name = "udpport")
    val udpPort: Int? = null,

    @Json(name = "live")
    val isReceivingRtData: Boolean? = null
)