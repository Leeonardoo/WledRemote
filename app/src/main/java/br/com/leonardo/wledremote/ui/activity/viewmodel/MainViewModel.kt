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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
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

                is LocalResultWrapper.Success -> _info.postValue(it.value!!)

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

                is LocalResultWrapper.Success -> _state.postValue(it.value!!)

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

                is LocalResultWrapper.Success -> _effects.postValue(it.value!!)

                is LocalResultWrapper.NetworkError -> {

                }

                is LocalResultWrapper.GenericError -> {

                }
            }

            setLoading()
        }
    }

    private fun getPalettes() = viewModelScope.launch {
        infoRepository.getPalettes().collect {
            isPalettesLoading = it == LocalResultWrapper.Loading

            when (it) {
                is LocalResultWrapper.Loading -> {
                }

                is LocalResultWrapper.Success -> _palettes.postValue(it.value!!)

                is LocalResultWrapper.NetworkError -> {

                }

                is LocalResultWrapper.GenericError -> {

                }
            }

            setLoading()
        }
    }

    fun onPowerToggled() {
        if (state.value != null) {
            state.value!!.on?.let {
                val state = State(on = !it)
                sendState(state)

                _state.postValue(this.state.value!!.copy(on = state.on))
            }
        }
    }

    fun setColor(colorInt: Int, colorIndex: Int) {
        val rgbColor = mutableListOf(colorInt.red, colorInt.green, colorInt.blue)

        // Update the color based on the index from the last segment that is selected
        val oldState = state.value
        var newState = State(segments = listOf(Segment(colors = listOf(rgbColor)))) // In case the default one was not set
        if (oldState != null) {
            val segments = oldState.segments?.toMutableList()

            segments?.forEachIndexed {index, segment ->
                if (segment?.selected == true) {
                    val colors = segment.colors?.toMutableList()
                    colors?.set(colorIndex, rgbColor)
                    val updateSegment = segment.copy(colors = colors)
                    segments[index] = updateSegment
                }
            }

            newState = State(segments = segments) // Will send updated segments

            // This will now make sure that the value is updated with the correct information in
            // our local device so that our views can be updated with our local changes
            val updatedState = oldState.copy(segments = segments)
            _state.postValue(updatedState)
        }

        sendState(newState)
    }

    fun setBrightness(brightness: Int) {
        val state = State(brightness = brightness)
        sendState(state)
    }

    fun setPalette(paletteId: Int) {
        var state = State(segments = listOf(Segment(paletteId = paletteId)))
        val oldState = this.state.value
        if (oldState != null) {
            val segments = oldState.segments?.toMutableList()

            segments?.forEachIndexed {index, segment ->
                if (segment?.selected == true) {
                    val updateSegment = segment.copy(paletteId = paletteId)
                    segments[index] = updateSegment
                }
            }

            state = State(segments = segments) // Will send updated segments

            // This will now make sure that the value is updated with the correct information in
            // our local device so that our views can be updated with our local changes
            val updatedState = oldState.copy(segments = segments)
            _state.postValue(updatedState)
        }
        sendState(state)
    }

    fun setEffect(effectId: Int) {
        var state = State(segments = listOf(Segment(effectId = effectId)))
        val oldState = this.state.value
        if (oldState != null) {
            val segments = oldState.segments?.toMutableList()

            segments?.forEachIndexed {index, segment ->
                if (segment?.selected == true) {
                    val updateSegment = segment.copy(effectId = effectId)
                    segments[index] = updateSegment
                }
            }

            state = State(segments = segments) // Will send updated segments

            // This will now make sure that the value is updated with the correct information in
            // our local device so that our views can be updated with our local changes
            val updatedState = oldState.copy(segments = segments)
            _state.postValue(updatedState)
        }
        sendState(state)
    }

    fun setEffectAttr(intensity: Int? = null, speed: Int? = null) {
        var state = State(segments = listOf(Segment(effectIntensity = intensity, relativeSpeed = speed)))
        val oldState = this.state.value
        if (oldState != null) {
            val segments = oldState.segments?.toMutableList()

            segments?.forEachIndexed {index, segment ->
                if (segment?.selected == true) {
                    var updateSegment: Segment?
                    if (intensity != null && speed != null) {
                        updateSegment = segment.copy(effectIntensity = intensity, relativeSpeed = speed)
                    } else if (intensity != null) {
                        updateSegment = segment.copy(effectIntensity = intensity)
                    } else {
                        updateSegment = segment.copy(relativeSpeed = speed)
                    }
                    segments[index] = updateSegment
                }
            }

            state = State(segments = segments) // Will send updated segments

            // This will now make sure that the value is updated with the correct information in
            // our local device so that our views can be updated with our local changes
            val updatedState = oldState.copy(segments = segments)
            _state.postValue(updatedState)
        }
        sendState(state)
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