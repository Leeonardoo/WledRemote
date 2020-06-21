package br.com.leonardo.wledremote.ui.fragment.viewmodel

import android.app.Application
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.leonardo.wledremote.repository.StateRepository
import br.com.leonardo.wledremote.rest.request.state.Segment
import br.com.leonardo.wledremote.rest.request.state.StateRequest
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(application)
    private val stateRepository = StateRepository()

    fun setColor(colorArray: Int) {
        val rgbColor = mutableListOf(
            colorArray.red,
            colorArray.green,
            colorArray.blue
        )
        //Hardcoded for the first one for now
        val state = StateRequest(segments = listOf(Segment(colors = listOf(rgbColor))))

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                stateRepository.sendState(state)
            }
        }
    }

    fun setBrightness(brightness: Int) {
        val state = StateRequest(brightness = brightness)

        viewModelScope.launch {
            withContext(Dispatchers.IO) { stateRepository.sendState(state) }
        }
    }
}