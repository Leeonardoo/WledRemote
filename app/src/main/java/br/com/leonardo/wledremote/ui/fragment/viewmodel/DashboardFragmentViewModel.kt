package br.com.leonardo.wledremote.ui.fragment.viewmodel

import android.app.Application
import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.leonardo.wledremote.repository.StateRepository
import br.com.leonardo.wledremote.rest.request.state.StateRequest
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(application)
    private val stateRepository = StateRepository()

    fun setColor(color: Int) {
        val hexColor = String.format("#%06X", (0xFFFFFF and color))
        val rgbColor = mutableListOf(
            Color.parseColor(hexColor).red,
            Color.parseColor(hexColor).green,
            Color.parseColor(hexColor).blue
        )

        /*val state = State(segment = listOf(Segment(colors = listOf(rgbColor))))

        CoroutineScope(Dispatchers.IO).launch {
            RetrofitConn.getInstance().wledEndpoint().setState(state)

        }*/
    }

    fun setBrightness(brightness: Int) {
        val state = StateRequest(brightness = brightness)

        viewModelScope.launch {
            withContext(Dispatchers.IO) { stateRepository.sendState(state) }
        }
    }
}