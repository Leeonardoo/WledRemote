package br.com.leonardo.wledremote.rest.request.state

import com.google.gson.annotations.SerializedName

data class NightLight(

	@field:SerializedName("dur")
	val duration: Int? = null,

	@field:SerializedName("fade")
	val fade: Boolean? = null,

	@field:SerializedName("tbri")
	val brightness: Int? = null,

	@field:SerializedName("on")
	val on: Boolean? = null
)