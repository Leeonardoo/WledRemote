package br.com.leonardo.wledremote.rest.request.state

import com.google.gson.annotations.SerializedName

data class Segment(

	@field:SerializedName("col")
	val colors: List<List<Int?>?>? = null,

	@field:SerializedName("rev")
	val reverse: Boolean? = null,

	@field:SerializedName("grp")
	val grp: Int? = null,

	@field:SerializedName("sx")
	val relativeSpeed: Int? = null,

	@field:SerializedName("spc")
	val spc: Int? = null,

	@field:SerializedName("start")
	val start: Int? = null,

	@field:SerializedName("ix")
	val effectIntensity: Int? = null,

	@field:SerializedName("fx")
	val effectId: Int? = null,

	@field:SerializedName("stop")
	val stop: Int? = null,

	@field:SerializedName("len")
	val lenght: Int? = null,

	@field:SerializedName("pal")
	val paletteId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("sel")
	val selected: Boolean? = null
)