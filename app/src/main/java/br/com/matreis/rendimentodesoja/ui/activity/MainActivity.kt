package br.com.matreis.rendimentodesoja.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.databinding.ActivityMainBinding
import br.com.matreis.rendimentodesoja.helper.PreferencesManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = PreferencesManager(this)
        if(preferences.getIsFirstRun()){
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        setUpNavigation()

    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment?
        val navMenu = binding.navMenu
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(navMenu, navController)
        //val navController = Navigation.findNavController(this, )
    }
}