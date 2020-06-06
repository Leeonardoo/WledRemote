package br.com.leonardo.wledremote.rest.request.state

import com.google.gson.annotations.SerializedName

data class StateRequest(

	@field:SerializedName("pss")
	val pss: Int? = null,

	@field:SerializedName("ps")
	val currentPreset: Int? = null,

	@field:SerializedName("mainseg")
	val mainSegment: Int? = null,

	@field:SerializedName("seg")
	val segments: List<Segment>? = null,

	@field:SerializedName("ccnf")
	val ccnf: Ccnf? = null,

	@field:SerializedName("bri")
	val brightness: Int? = null,

	@field:SerializedName("udpn")
	val udpn: Udpn? = null,

	@field:SerializedName("pl")
	val currentPlaylist: Int? = null,

	@field:SerializedName("transition")
	val transition: Int? = null,

	@field:SerializedName("nl")
	val nightLight: NightLight? = null,

	@field:SerializedName("on")
	val on: Boolean? = null
)