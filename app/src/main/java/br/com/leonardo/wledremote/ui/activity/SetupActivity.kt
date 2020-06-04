package br.com.leonardo.wledremote.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.ui.activity.viewmodel.SetupActivityViewModel
import kotlinx.android.synthetic.main.activity_setup.*

class SetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        val viewModel = ViewModelProvider(this).get(SetupActivityViewModel::class.java)

        connect_button.setOnClickListener {
            viewModel.sharedPrefsUtil.setConfigIP(ip = ip_text.text.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
