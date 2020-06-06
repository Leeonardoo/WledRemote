package br.com.leonardo.wledremote.model.info

import com.google.gson.annotations.SerializedName

data class Leds(

	@field:SerializedName("pin")
	val pin: List<Int>,

	@field:SerializedName("seglock")
	val segLock: Boolean,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("maxpwr")
	val maxPower: Int,

	@field:SerializedName("pwr")
	val currentPowerUsage: Int,

	@field:SerializedName("maxseg")
	val maxSegments: Int,

	@field:SerializedName("rgbw")
	val rgbw: Boolean,

	@field:SerializedName("wv")
	val wv: Boolean
)