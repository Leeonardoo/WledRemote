package br.com.leonardo.wledremote

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WledApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getAppContext(): Context {
            return context
        }
    }
}