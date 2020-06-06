package br.com.leonardo.wledremote.model.state

import com.google.gson.annotations.SerializedName

data class Ccnf(

	@field:SerializedName("min")
	val min: Int,

	@field:SerializedName("max")
	val max: Int,

	@field:SerializedName("time")
	val time: Int
)