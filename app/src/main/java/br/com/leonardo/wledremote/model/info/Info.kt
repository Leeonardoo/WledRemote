package br.com.leonardo.wledremote.model.info

import com.google.gson.annotations.SerializedName

data class Info(

	@field:SerializedName("lwip")
	val lwip: Int,

	@field:SerializedName("ver")
	val versionName: String,

	@field:SerializedName("wifi")
	val wifi: Wifi,

	@field:SerializedName("product")
	val productName: String,

	@field:SerializedName("palcount")
	val palettesCount: Int,

	@field:SerializedName("mac")
	val macAddress: String,

	@field:SerializedName("freeheap")
	val freeHeap: Int,

	@field:SerializedName("uptime")
	val uptime: Int,

	@field:SerializedName("vid")
	val buildId: Int,

	@field:SerializedName("str")
	val str: Boolean,

	@field:SerializedName("core")
	val arduinoVersion: String,

	@field:SerializedName("opt")
	val opt: Int,

	@field:SerializedName("fxcount")
	val effectsCount: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("leds")
	val leds: Leds,

	@field:SerializedName("arch")
	val platformName: String,

	@field:SerializedName("brand")
	val brand: String,

	@field:SerializedName("udpport")
	val udpPort: Int,

	@field:SerializedName("live")
	val isReceivingRtData: Boolean
)