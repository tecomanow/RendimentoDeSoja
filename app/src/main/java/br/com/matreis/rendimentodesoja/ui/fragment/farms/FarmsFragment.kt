package br.com.matreis.rendimentodesoja.ui.fragment.farms

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.matreis.rendimentodesoja.App
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepositoryImp
import br.com.matreis.rendimentodesoja.data.repository.datasourceImp.LocalDatasourceImp
import br.com.matreis.rendimentodesoja.databinding.DialogEditBlockBinding
import br.com.matreis.rendimentodesoja.databinding.DialogEditFarmBinding
import br.com.matreis.rendimentodesoja.databinding.FragmentFarmsBinding
import br.com.matreis.rendimentodesoja.ui.activity.newfarm.FarmViewModel
import br.com.matreis.rendimentodesoja.ui.activity.newfarm.NewFarmActivity
import br.com.matreis.rendimentodesoja.ui.adapter.FarmsAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class FarmsFragment : Fragment() {

    private lateinit var binding : FragmentFarmsBinding
    private lateinit var farmsViewModel: FarmsFragmentViewModel
    private val farmAdapter by lazy { FarmsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFarmsBinding.inflate(inflater, container, false)
        farmsViewModel = ViewModelProvider(this, FarmsFragmentViewModel.FarmsFragmentViewModelFactory(
            SoybeanCalculatorRepositoryImp(
                LocalDatasourceImp(
                    (requireActivity().application as App).database.getFarmDao(),
                    (requireActivity().application as App).database.getBlockDao(),
                    (requireActivity().application as App).database.getEstimateDao()
                )
            )
        ))[FarmsFragmentViewModel::class.java]

        setUpRecyclerView()
        setUpAdapterListeners()
        setUpListener()
        setUpObserver()

        return binding.root
    }

    private fun setUpAdapterListeners() {
        farmAdapter.listenerDelete = {
            val build = AlertDialog.Builder(requireActivity())
            build.setTitle("Deseja excluir essa fazenda?")
            build.setMessage("Removê-la implicará em excluir todas as quadras e estimativas associadas à essa fazenda.")
            build.setPositiveButton("Sim") { _, _ ->
                farmsViewModel.deleteFarm(it)
                Snackbar.make(binding.clRoot, "Fazenda excluída com sucesso", Snackbar.LENGTH_LONG).show()
            }
            build.setNegativeButton("Não", null)
            build.show()
        }

        farmAdapter.listenerEdit = {
            openDialogEditFarm(it)
        }
    }

    private fun openDialogEditFarm(farm: Farm) {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        val binding = DialogEditFarmBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        val dialog = builder.show()
        binding.apply {
            editTextFarmName.setText(farm.name)
            editTextFarmCity.setText(farm.city)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            btnSave.setOnClickListener {
                val blockName = editTextFarmName.text.toString()
                val city = editTextFarmCity.text.toString()

                if(blockName.isNotBlank() && city.isNotBlank()){
                    farm.name = blockName
                    farm.city = city

                    farmsViewModel.updateFarm(farm)
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvFarms.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = farmAdapter
        }
    }

    private fun setUpObserver() {
        farmsViewModel.getAllFarms().observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                setEmptyVisibility(true)
            }else{
                farmAdapter.setList(it)
                setEmptyVisibility(false)
            }
        }
    }

    private fun setEmptyVisibility(visibility: Boolean) {
        if(visibility){
            binding.rvFarms.visibility = View.GONE
            binding.llEmptyFarm.visibility = View.VISIBLE
        }else{
            binding.rvFarms.visibility = View.VISIBLE
            binding.llEmptyFarm.visibility = View.GONE
        }
    }

    private fun setUpListener() {
        binding.fabAddNewFarm.setOnClickListener {
            startActivity(Intent(requireActivity(), NewFarmActivity::class.java))
        }
    }

}