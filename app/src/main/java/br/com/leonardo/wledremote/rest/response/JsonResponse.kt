package br.com.leonardo.wledremote.rest.response

import br.com.leonardo.wledremote.model.Effects
import br.com.leonardo.wledremote.model.Info
import br.com.leonardo.wledremote.model.Palettes
import br.com.leonardo.wledremote.model.State
import com.google.gson.annotations.SerializedName

data class JsonResponse(
    @SerializedName("state")
    var state: State,
    @SerializedName("info")
    var info: Info,
    @SerializedName("effects")
    var effects: Effects,
    @SerializedName("palettes")
    var palettes: Palettes
)