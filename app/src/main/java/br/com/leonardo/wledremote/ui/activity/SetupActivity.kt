package br.com.leonardo.wledremote.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.databinding.ActivitySetupBinding
import br.com.leonardo.wledremote.ui.activity.viewmodel.SetupViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import br.com.leonardo.wledremote.util.WledDialogUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding
    private val viewModel: SetupViewModel by viewModels()
    private lateinit var sharedPrefs: SharedPrefsUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPrefs = SharedPrefsUtil.getInstance(this)
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        if (sharedPrefs.isIPConfigured())
            navigateToMain()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setup)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.connectButton.setOnClickListener {
            sharedPrefs.setConfigIP(binding.ipText.text.toString())
            viewModel.getInfo()
        }
    }

    private fun setObservers() {
        viewModel.info.observe(this, {
            sharedPrefs.setIsIPConfigured(true)
            navigateToMain()
        })
        viewModel.infoError.observe(this, { showError(it) })
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(text: String) {
        WledDialogUtil.infoDialog(
            this, getString(R.string.connection_failed),
            text, getString(R.string.ok)
        )
    }
}
