package br.com.leonardo.wledremote.ui.viewmodel

import android.app.Application
import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.AndroidViewModel
import br.com.leonardo.wledremote.model.Segment
import br.com.leonardo.wledremote.model.State
import br.com.leonardo.wledremote.rest.api.RetrofitConn
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefsUtil = SharedPrefsUtil.getInstance(application)

    fun setColor(color: Int) {
        val hexColor = String.format("#%06X", (0xFFFFFF and color))
        val rgbColor = IntArray(3)

        for (i in rgbColor.indices) {
            when (i) {
                0 -> rgbColor[i] = Color.parseColor(hexColor).red
                1 -> rgbColor[i] = Color.parseColor(hexColor).green
                2 -> rgbColor[i] = Color.parseColor(hexColor).blue
            }
        }

        val colors = State(segment = arrayOf(Segment(colors = arrayOf(rgbColor))))

        GlobalScope.launch {
            RetrofitConn.getInstance().wledEndpoint().setColors(colors)

        }
    }
}