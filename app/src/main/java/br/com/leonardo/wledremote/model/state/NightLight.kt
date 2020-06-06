package br.com.leonardo.wledremote.model.state

import com.google.gson.annotations.SerializedName

data class NightLight(

	@field:SerializedName("dur")
	val duration: Int,

	@field:SerializedName("fade")
	val fade: Boolean,

	@field:SerializedName("tbri")
	val brightness: Int,

	@field:SerializedName("on")
	val on: Boolean
)