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

class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding
    private val viewModel: SetupActivityViewModel by viewModels()
    private lateinit var sharedPrefs: SharedPrefsUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPrefs = SharedPrefsUtil.getInstance(this)
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        if (sharedPrefs.isIPConfigured())
            startActivity(Intent(this, MainActivity::class.java))
        else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_setup)
            binding.connectButton.setOnClickListener {
                sharedPrefs.setConfigIP(ip = binding.ipText.text.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
