package br.com.leonardo.wledremote.model.state

import com.google.gson.annotations.SerializedName

data class Udpn(

	@field:SerializedName("recv")
	val recv: Boolean,

	@field:SerializedName("send")
	val send: Boolean
)