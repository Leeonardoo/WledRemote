package br.com.leonardo.wledremote.model.state

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class State(

    @Json(name = "pss")
    val pss: Int? = null,

    @Json(name = "ps")
    val currentPreset: Int? = null,

    @Json(name = "mainseg")
    val mainSegment: Int? = null,

    @Json(name = "seg")
    val segments: List<Segment?>? = null,

    val ccnf: Ccnf? = null,

    @Json(name = "bri")
    val brightness: Int? = null,

    val udpn: Udpn? = null,

    @Json(name = "pl")
    val currentPlaylist: Int? = null,

    @Json(name = "transition")
    val transition: Int? = null,

    @Json(name = "nl")
    val nightLight: NightLight? = null,

    @Json(name = "on")
    val on: Boolean? = null
)