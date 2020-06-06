package br.com.leonardo.wledremote.model.state

import com.google.gson.annotations.SerializedName

data class Segment(

	@field:SerializedName("col")
	val colors: List<List<Int>>,

	@field:SerializedName("rev")
	val reverse: Boolean,

	@field:SerializedName("grp")
	val grp: Int,

	@field:SerializedName("sx")
	val relativeSpeed: Int,

	@field:SerializedName("spc")
	val spc: Int,

	@field:SerializedName("start")
	val start: Int,

	@field:SerializedName("ix")
	val effectIntensity: Int,

	@field:SerializedName("fx")
	val effectId: Int,

	@field:SerializedName("stop")
	val stop: Int,

	@field:SerializedName("len")
	val lenght: Int,

	@field:SerializedName("pal")
	val paletteId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("sel")
	val selected: Boolean
)