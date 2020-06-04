package br.com.leonardo.wledremote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NightLight(
    var on: Boolean,

    @SerializedName("dur")
    var duration: Int,

    var fade: Boolean,

    @SerializedName("tbri")
    var targetBrightness: Int
)