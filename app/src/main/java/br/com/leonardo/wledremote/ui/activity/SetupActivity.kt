package br.com.leonardo.wledremote.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.databinding.ActivitySetupBinding
import br.com.leonardo.wledremote.ui.activity.viewmodel.SetupViewModel
import br.com.leonardo.wledremote.util.SharedPrefsUtil
import br.com.leonardo.wledremote.util.WledDialogUtil

class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding
    private val viewModel: SetupViewModel by viewModels()
    private lateinit var sharedPrefs: SharedPrefsUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPrefs = SharedPrefsUtil.getInstance(this)
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        if (sharedPrefs.isIPConfigured())
            startActivity(Intent(this, MainActivity::class.java))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setup)
        binding.lifecycleOwner = this
        binding.connectButton.setOnClickListener {
            sharedPrefs.setConfigIP(ip = binding.ipText.text.toString())
            viewModel.getDeviceInfo()
        }
        setObservers()
    }

    private fun setObservers() {
        viewModel.info.observe(this, Observer {
//            when (it) {
//                is InfoStatus.Success -> {
            val intent = Intent(this, MainActivity::class.java)
            sharedPrefs.setIsIPConfigured(true)
            startActivity(intent)
            finish()
//                }
//
//                is InfoStatus.Loading -> {
//                    binding.connecting.visibility = View.VISIBLE
//                    binding.connectButton.visibility = View.INVISIBLE
//                }
//
//                is InfoStatus.GenericError -> showError(it.error)
//                is InfoStatus.NetworkError -> showError(it.error)
//            }
        })
    }

    private fun showError(text: String?) {
        binding.connecting.visibility = View.GONE
        binding.connectButton.visibility = View.VISIBLE

        val message =
            if (text.isNullOrBlank()) getString(R.string.connection_failed_message)
            else getString(R.string.connection_failed_message_extras, text)

        WledDialogUtil.infoDialog(
            this,
            getString(R.string.connection_failed),
            message, getString(R.string.retry)
        )
    }
}
