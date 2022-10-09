package br.com.matreis.rendimentodesoja.ui.activity.newfarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import br.com.matreis.rendimentodesoja.App
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepository
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepositoryImp
import br.com.matreis.rendimentodesoja.data.repository.datasourceImp.LocalDatasourceImp
import br.com.matreis.rendimentodesoja.databinding.ActivityNewFarmBinding

class NewFarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewFarmBinding
    private lateinit var farmViewModel: FarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewFarmBinding.inflate(layoutInflater)
        farmViewModel = ViewModelProvider(this, FarmViewModel.MyFarmViewModelFactory(
            SoybeanCalculatorRepositoryImp(
                LocalDatasourceImp(
                    (application as App).database.getFarmDao(),
                    (application as App).database.getBlockDao(),
                    (application as App).database.getEstimateDao()
                )
            )
        ))[FarmViewModel::class.java]

        setUpCustomToolbar()
        setUpListener()

        setContentView(binding.root)
    }

    private fun setUpCustomToolbar() {
        binding.toolbar.textToolbar.text = "Nova fazenda"
        binding.toolbar.imgBtnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpListener() {
        binding.btnSave.setOnClickListener {
            if(checkInputs()){
                farmViewModel.insertFarm(
                    binding.editTextFarmName.text.toString(),
                    binding.editTextFarmCity.text.toString()
                )
            }
            finish()
        }
    }

    private fun checkInputs(): Boolean {
        val name = binding.editTextFarmName.text.toString()
        val city = binding.editTextFarmCity.text.toString()

        return if(name.isNotBlank()){
            if(city.isNotBlank()){
                true
            }else{
                binding.editTextFarmCity.error = "Insira o município da fazenda"
                false
            }
        }else{
            binding.editTextFarmName.error = "Insira o nome da fazenda"
            false
        }
    }
}