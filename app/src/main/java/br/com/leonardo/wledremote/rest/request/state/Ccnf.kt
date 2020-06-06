package br.com.leonardo.wledremote.rest.request.state

import com.google.gson.annotations.SerializedName

data class Ccnf(

	@field:SerializedName("min")
	val min: Int? = null,

	@field:SerializedName("max")
	val max: Int? = null,

	@field:SerializedName("time")
	val time: Int? = null
)