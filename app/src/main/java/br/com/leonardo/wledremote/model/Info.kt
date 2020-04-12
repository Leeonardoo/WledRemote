package br.com.leonardo.wledremote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Info(
    @SerializedName("ver")
    var version: String,
    @SerializedName("vid")
    var buildId: Int,
    var leds: Leds,
    var name: String,
    @SerializedName("udpport")
    var udpPort: Int,
    @SerializedName("live")
    var isRealtimeDataStreaming: Boolean,
    @SerializedName("fxcount")
    var fxCount: Int,
    @SerializedName("palCount")
    var paletteCount: Int,
    @SerializedName("arch")
    var boardArch: String,
    @SerializedName("core")
    var arduinoSdkVersion: String,
    @SerializedName("freeheap")
    var freeHeap: Int,
    @SerializedName("uptime")
    var upTime: Int,
    @SerializedName("opt")
    var debugInfo: Int,
    var brand: String,
    var product: String,
    @SerializedName("btype")
    var buildType: String,
    @SerializedName("mac")
    var macAddress: String
) : Serializable