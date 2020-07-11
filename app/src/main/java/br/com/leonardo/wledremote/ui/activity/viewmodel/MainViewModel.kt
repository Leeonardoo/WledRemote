package br.com.leonardo.wledremote.ui.activity.viewmodel

import android.app.Application
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.leonardo.wledremote.model.info.Info
import br.com.leonardo.wledremote.model.state.Segment
import br.com.leonardo.wledremote.model.state.State
import br.com.leonardo.wledremote.repository.InfoRepository
import br.com.leonardo.wledremote.repository.StateRepository
import br.com.leonardo.wledremote.rest.api.LocalResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val stateRepository = StateRepository()
    private val infoRepository = InfoRepository()
    val currentState = stateRepository.stateResponse
    val palettes = infoRepository.paletteResponse
    val effects = infoRepository.effectResponse

    private val infoResponse = infoRepository.infoResponse

    private val _infoLoading = MutableLiveData<Boolean>()
    val infoLoading: LiveData<Boolean> = _infoLoading
    //We want it to be global for all the fragments
    private val _info = MutableLiveData<Info>()
    val info: LiveData<Info> = _info

    init {
        getState()
        getInfo()
        getPalettes()
        getEffects()
        flowInfo()
    }

    fun getInfo() = viewModelScope.launch { infoRepository.getInfo() }

    fun getState() = viewModelScope.launch { stateRepository.getState() }

    fun getEffects() = viewModelScope.launch { infoRepository.getEffects() }

    fun getPalettes() = viewModelScope.launch { infoRepository.getPalettes() }

    fun onPowerClicked() {
        if (currentState.value is LocalResultWrapper.Success) {
            (currentState.value as LocalResultWrapper.Success).value.on?.let {
                val state = State(on = !it)
                sendState(state)
            }
        }
    }

    fun setColor(colorArray: Int) {
        val rgbColor = mutableListOf(colorArray.red, colorArray.green, colorArray.blue)
        //Hardcoded for the first one for now
        val state = State(segments = listOf(Segment(colors = listOf(rgbColor))))
        sendState(state)
    }

    fun setBrightness(brightness: Int) {
        val state = State(brightness = brightness)
        sendState(state)
    }

    fun setPalette(paletteId: Int) {
        val state = State(segments = listOf(Segment(paletteId = paletteId)))
        sendState(state)
    }

    fun setEffect(effectId: Int) {
        val state = State(segments = listOf(Segment(effectId = effectId)))
        sendState(state)
    }

    fun setEffectAttr(intensity: Int? = null, speed: Int? = null) {
        val state = State(
            segments = listOf(
                Segment(effectIntensity = intensity, relativeSpeed = speed)
            )
        )
        sendState(state)
    }

    private fun flowInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                infoResponse.collect {
                    when (it) {
                        is LocalResultWrapper.Loading -> _infoLoading.postValue(true)
                        is LocalResultWrapper.Success -> {
                            _info.postValue(it.value)
                            _infoLoading.postValue(false)
                        }
                    }
                }
            }
        }
    }

    private fun sendState(state: State) {
        viewModelScope.launch { stateRepository.sendState(state) }
    }
}