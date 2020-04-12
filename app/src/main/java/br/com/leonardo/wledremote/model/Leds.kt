package br.com.leonardo.wledremote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Leds(
    var count: Int,
    @SerializedName("rgbw")
    var isRgbW: Boolean,
    var pin: Array<Int>,
    @SerializedName("pwr")
    var currentPower: Int,
    @SerializedName("maxpwr")
    var maxPower: Int,
    @SerializedName("maxseg")
    var maxSegments: Int
) : Serializable