package br.com.leonardo.wledremote.model.state

import com.google.gson.annotations.SerializedName

data class State(

	@field:SerializedName("pss")
	val pss: Int,

	@field:SerializedName("ps")
	val currentPreset: Int,

	@field:SerializedName("mainseg")
	val mainSegment: Int,

	@field:SerializedName("seg")
	val segments: List<Segment>,

	@field:SerializedName("ccnf")
	val ccnf: Ccnf,

	@field:SerializedName("bri")
	val brightness: Int,

	@field:SerializedName("udpn")
	val udpn: Udpn,

	@field:SerializedName("pl")
	val currentPlaylist: Int,

	@field:SerializedName("transition")
	val transition: Int,

	@field:SerializedName("nl")
	val nightLight: NightLight,

	@field:SerializedName("on")
	val on: Boolean
)