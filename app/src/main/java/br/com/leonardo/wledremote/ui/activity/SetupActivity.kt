package br.com.leonardo.wledremote.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.databinding.ActivitySetupBinding
import br.com.leonardo.wledremote.ui.activity.viewmodel.SetupActivityViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import kotlinx.android.synthetic.main.activity_setup.*

class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding
    private val viewModel: SetupActivityViewModel by viewModels()
    private lateinit var sharedPrefsUtil: SharedPrefsUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        if (viewModel.isSetup())
            startActivity(Intent(this, MainActivity::class.java))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setup)

        sharedPrefsUtil = SharedPrefsUtil.getInstance(this)

        binding.connectButton.setOnClickListener {
            sharedPrefsUtil.setConfigIP(ip = binding.ipText.text.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
