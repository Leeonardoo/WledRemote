package br.com.leonardo.wledremote.ui.fragment.viewmodel

import android.app.Application
import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.AndroidViewModel
import br.com.leonardo.wledremote.rest.api.RetrofitConn
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(application)

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
        /*val state = State(brightness = brightness)

        CoroutineScope(Dispatchers.IO).launch {
            RetrofitConn.getInstance().wledEndpoint().setState(state)

        }*/
    }
}