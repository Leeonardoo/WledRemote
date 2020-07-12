package br.com.leonardo.wledremote.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import br.com.leonardo.wledremote.R
import br.com.leonardo.wledremote.databinding.ActivityMainBinding
import br.com.leonardo.wledremote.ui.activity.viewmodel.MainViewModel
import br.com.leonardo.wledremote.util.setupWithNavController
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null)
            setupBottomNavigationBar()

        setObservers()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState!!)
        setupBottomNavigationBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_power -> {
            viewModel.onPowerClicked()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun setObservers() {
        viewModel.sendStateError.observe(this, Observer {
            Snackbar.make(binding.navHostFragment, it, Snackbar.LENGTH_LONG).apply {
                anchorView = binding.bottomNav
            }.show()
        })
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.bottomNav
        val navGraphIds = listOf(
            R.navigation.bottom_nav_dashboard,
            R.navigation.bottom_nav_effects,
            R.navigation.bottom_nav_settings
        )

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        controller.observe(this, Observer { navController ->
            /*
             * A little, less awful hack to use our custom toolbar title, by setting it to null and
             * setting setDisplayShowTitleEnabled to false. It happens every time the user navigates
             * to another navGraph or inside the same navGraph, as we always remove and add a listener
             */

            binding.toolbar.setupWithNavController(navController)

            val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                binding.toolbarTitle.text = destination.label
                binding.toolbar.title = null
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }

            navController.removeOnDestinationChangedListener(listener)
            navController.addOnDestinationChangedListener(listener)
            binding.toolbar.title = null
            supportActionBar?.setDisplayShowTitleEnabled(false)
        })

        currentNavController = controller
    }
}
