package br.com.leonardo.wledremote.ui.activity.viewmodel

import android.app.Application
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.leonardo.wledremote.repository.InfoRepository
import br.com.leonardo.wledremote.repository.StateRepository
import br.com.leonardo.wledremote.repository.StateStatus
import br.com.leonardo.wledremote.rest.request.state.Segment
import br.com.leonardo.wledremote.rest.request.state.StateRequest
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val stateRepository = StateRepository()
    private val infoRepository = InfoRepository()
    private val currentState = stateRepository.stateResponse
    val palettes = infoRepository.paletteResponse
    val effects = infoRepository.effectResponse

    init {
        viewModelScope.launch {
            stateRepository.getState()
            infoRepository.getPalettes()
            infoRepository.getEffects()
        }
    }

    fun onPowerClicked() {
        if (currentState.value is StateStatus.Success) {
            viewModelScope.launch {
                val stateRequest =
                    StateRequest(on = !(currentState.value as StateStatus.Success).state.on)
                stateRepository.sendState(stateRequest)
            }
        }
    }

    fun setColor(colorArray: Int) {
        val rgbColor = mutableListOf(colorArray.red, colorArray.green, colorArray.blue)
        //Hardcoded for the first one for now
        val state = StateRequest(segments = listOf(Segment(colors = listOf(rgbColor))))
        viewModelScope.launch { stateRepository.sendState(state) }
    }

    fun setBrightness(brightness: Int) {
        val state = StateRequest(brightness = brightness)
        viewModelScope.launch { stateRepository.sendState(state) }
    }

    fun setPalette(paletteId: Int) {
        val state = StateRequest(segments = listOf(Segment(paletteId = paletteId)))
        viewModelScope.launch { stateRepository.sendState(state) }
    }
}