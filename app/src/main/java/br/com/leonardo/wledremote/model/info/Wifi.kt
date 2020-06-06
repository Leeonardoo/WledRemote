package br.com.leonardo.wledremote.model.info

import com.google.gson.annotations.SerializedName

data class Wifi(

	@field:SerializedName("rssi")
	val rssi: Int,

	@field:SerializedName("bssid")
	val bssid: String,

	@field:SerializedName("channel")
	val channel: Int,

	@field:SerializedName("signal")
	val signal: Int
)