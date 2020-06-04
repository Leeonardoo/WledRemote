package br.com.leonardo.wledremote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Udpn(
    var send: Boolean,

    @SerializedName("recv")
    var receive: Boolean
)
