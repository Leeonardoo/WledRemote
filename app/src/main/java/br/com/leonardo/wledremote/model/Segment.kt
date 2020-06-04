package br.com.leonardo.wledremote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Segment(
    var start: Int? = null,

    var stop: Int? = null,

    @SerializedName("len")
    var lenght: Int? = null,

    @SerializedName("col")
    var colors: List<List<Int>>? = null,

    @SerializedName("fx")
    var fxId: Int? = null,

    @SerializedName("sx")
    var relativeSpeed: Int? = null,

    @SerializedName("ix")
    var effectIntensity: Int? = null,

    @SerializedName("pal")
    var paletteId: Int? = null,

    @SerializedName("sel")
    var isSelected: Boolean? = null,

    @SerializedName("rev")
    var reverse: Boolean? = null,

    @SerializedName("cln")
    var cloneTo: Int? = null
)