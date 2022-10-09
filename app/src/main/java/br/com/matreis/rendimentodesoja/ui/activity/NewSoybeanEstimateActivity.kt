package br.com.matreis.rendimentodesoja.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.databinding.ActivityNewSoybeanEstimateBinding

class NewSoybeanEstimateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewSoybeanEstimateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewSoybeanEstimateBinding.inflate(layoutInflater)

        setUpCustomToolbar()
        setUpNavigation()

        setContentView(binding.root)
    }

    private fun setUpCustomToolbar() {
        binding.toolbar.textToolbar.text = "Nova estimativa"
        binding.toolbar.imgBtnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_estimate) as NavHostFragment?
        val navController = navHostFragment!!.navController
        //val navController = Navigation.findNavController(this, )
    }
}