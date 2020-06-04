package br.com.leonardo.wledremote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class State(
    var on: Boolean? = null,

    @SerializedName("bri")
    var brightness: Int? = null,

    var transition: Int? = null,

    @SerializedName("ps")
    var presetId: Int? = null,

    @SerializedName("pl")
    var playlistId: Int? = null,

    @SerializedName("nl")
    var nightLight: NightLight? = null,

    var udpn: Udpn? = null,

    @SerializedName("seg")
    var segment: List<Segment>? = null
)