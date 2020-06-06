package br.com.leonardo.wledremote.rest.request.state

import com.google.gson.annotations.SerializedName

data class Udpn(

	@field:SerializedName("recv")
	val recv: Boolean? = null,

	@field:SerializedName("send")
	val send: Boolean? = null
)