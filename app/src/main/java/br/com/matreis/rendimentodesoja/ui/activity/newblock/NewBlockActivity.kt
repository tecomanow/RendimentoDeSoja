package br.com.matreis.rendimentodesoja.ui.activity.newblock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.matreis.rendimentodesoja.App
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepositoryImp
import br.com.matreis.rendimentodesoja.data.repository.datasourceImp.LocalDatasourceImp
import br.com.matreis.rendimentodesoja.databinding.ActivityNewBlockBinding
import br.com.matreis.rendimentodesoja.ui.activity.newfarm.FarmViewModel

class NewBlockActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityNewBlockBinding
    private lateinit var newBlockViewModel: NewBlockViewModel
    private var idFarm = 0L
    private val farmsName = ArrayList<String>()
    private var farmsList : List<Farm>? = null
    private var mSystem = -1

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBlockBinding.inflate(layoutInflater)
        newBlockViewModel = ViewModelProvider(this, NewBlockViewModel.NewBlockViewModelFactory(
            SoybeanCalculatorRepositoryImp(
                LocalDatasourceImp(
                    (application as App).database.getFarmDao(),
                    (application as App).database.getBlockDao(),
                    (application as App).database.getEstimateDao()
                )
            )
        ))[NewBlockViewModel::class.java]

        farmsName.add("Selecione uma fazenda")

        getMeasurementSystem()
        setUpCustomToolbar()
        setUpListener()
        setUpObservers()

        setContentView(binding.root)
    }

    private fun getMeasurementSystem() {
        mSystem = newBlockViewModel.getMeasurementSystem(this)
        if(mSystem == 0){
            binding.textViewBlockSize.text = "Tamanho da quadra (ha)"
        }else if(mSystem == 1){
            binding.textViewBlockSize.text = "Tamanho da quadra (acre)"
        }
    }

    private fun setUpCustomToolbar() {
        binding.toolbar.textToolbar.text = "Nova quadra"
        binding.toolbar.imgBtnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpObservers() {
        newBlockViewModel.getAllFarm().observe(this){
            setUpSpinner(it)
        }
    }

    private fun setUpSpinner(farms: List<Farm>?) {
        farms?.let {

            farms.forEach {
                farmsName.add(it.name)
            }

            ArrayAdapter(this, android.R.layout.simple_spinner_item, farmsName).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerFarm.adapter = adapter
            }

            farmsList = it

        }
    }

    private fun setUpListener() {
        binding.btnSave.setOnClickListener {
            if(checkInputs()){
                val block = Block(
                    idBlock = 0,
                    idFarm = idFarm,
                    binding.editTextBlockName.text.toString(),
                    binding.editTextBlockSize.text.toString().toDouble(),
                    mSystem
                )
                newBlockViewModel.insertBlock(block)
                finish()
            }
        }
        binding.spinnerFarm.onItemSelectedListener = this
    }

    private fun checkInputs(): Boolean {
        return true
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if(p2 == 0){
            Log.i("MYTAG", "Escolha uma fazenda, position: ${p2}")
        }else if(p2 > 0){
            farmsList?.let {
                Log.i("MYTAG", "${it.get(p2-1).name} position: ${p2}")
                idFarm = it.get(p2-1).id
            }
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}