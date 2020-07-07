package br.com.leonardo.wledremote.model.state

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Segment(

    @Json(name = "col")
    val colors: List<List<Int?>?>? = null,

    @Json(name = "rev")
    val reverse: Boolean? = null,

    @Json(name = "grp")
    val grp: Int? = null,

    @Json(name = "sx")
    val relativeSpeed: Int? = null,

    @Json(name = "spc")
    val spc: Int? = null,

    @Json(name = "start")
    val start: Int? = null,

    @Json(name = "ix")
    val effectIntensity: Int? = null,

    @Json(name = "fx")
    val effectId: Int? = null,

    @Json(name = "stop")
    val stop: Int? = null,

    @Json(name = "len")
    val lenght: Int? = null,

    @Json(name = "pal")
    val paletteId: Int? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "sel")
    val selected: Boolean? = null
)