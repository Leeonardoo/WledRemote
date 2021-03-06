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
import br.com.leonardo.wledremote.util.ActionLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val stateRepository = StateRepository()
    private val infoRepository = InfoRepository()

    private var isStateLoading = true
    private var isPalettesLoading = true
    private var isEffectsLoading = true
    private var isInfoLoading = true

    //We want it to be global for all the fragments
    private val _info = MutableLiveData<Info>()
    val info: LiveData<Info> = _info

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _palettes = MutableLiveData<List<String>>()
    val palettes: LiveData<List<String>> = _palettes

    private val _effects = MutableLiveData<List<String>>()
    val effects: LiveData<List<String>> = _effects

    //Loading status
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    //Error status
    private val _sendStateError = ActionLiveData<String>()
    val sendStateError: LiveData<String> = _sendStateError

    init {
        getState()
        getInfo()
        getPalettes()
        getEffects()
    }

    fun getInfo() = viewModelScope.launch {
        infoRepository.getInfo().collect {
            isInfoLoading = it == LocalResultWrapper.Loading

            when (it) {
                is LocalResultWrapper.Loading -> {
                }

                is LocalResultWrapper.Success -> _info.postValue(it.value)

                is LocalResultWrapper.NetworkError -> {

                }

                is LocalResultWrapper.GenericError -> {

                }
            }

            setLoading()
        }
    }

    fun getState() = viewModelScope.launch {
        stateRepository.getState().collect {
            isStateLoading = it == LocalResultWrapper.Loading

            when (it) {
                is LocalResultWrapper.Loading -> {
                }

                is LocalResultWrapper.Success -> {

                }

                is LocalResultWrapper.NetworkError -> {

                }

                is LocalResultWrapper.GenericError -> {

                }
            }

            setLoading()
        }
    }

    fun getEffects() = viewModelScope.launch {
        infoRepository.getEffects().collect {
            isEffectsLoading = it == LocalResultWrapper.Loading
            when (it) {
                is LocalResultWrapper.Loading -> {
                }

                is LocalResultWrapper.Success -> {
                    _effects.postValue(it.value)
                }

                is LocalResultWrapper.NetworkError -> {

                }

                is LocalResultWrapper.GenericError -> {

                }
            }

            setLoading()
        }
    }

    fun getPalettes() = viewModelScope.launch {
        infoRepository.getPalettes().collect {
            isPalettesLoading = it == LocalResultWrapper.Loading

            when (it) {
                is LocalResultWrapper.Loading -> {
                }

                is LocalResultWrapper.Success -> {
                    _palettes.postValue(it.value)
                }

                is LocalResultWrapper.NetworkError -> {

                }

                is LocalResultWrapper.GenericError -> {

                }
            }

            setLoading()
        }
    }

    fun onPowerClicked() {
        if (state.value != null) {
            state.value!!.on?.let {
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
        val state =
            State(segments = listOf(Segment(effectIntensity = intensity, relativeSpeed = speed)))

        sendState(state)
    }

    fun refreshAll() {
        getState()
        getInfo()
        getPalettes()
        getEffects()
    }

    private fun sendState(state: State) {
        viewModelScope.launch {
            stateRepository.sendState(state).collect {
                when (it) {
                    is LocalResultWrapper.GenericError ->
                        withContext(Dispatchers.Main) { _sendStateError.sendAction(it.error) }

                    is LocalResultWrapper.NetworkError ->
                        withContext(Dispatchers.Main) { _sendStateError.sendAction(it.error) }

                    else -> {
                    }
                }
            }
        }
    }

    private fun setLoading() {
        _isLoading.postValue(
            isInfoLoading && isPalettesLoading && isEffectsLoading && isInfoLoading
        )
    }
}