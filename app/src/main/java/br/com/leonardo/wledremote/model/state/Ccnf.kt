package br.com.leonardo.wledremote.model.state

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ccnf(
    val min: Int? = null,
    val max: Int? = null,
    val time: Int? = null
)