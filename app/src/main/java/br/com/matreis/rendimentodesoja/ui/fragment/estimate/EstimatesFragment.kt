package br.com.matreis.rendimentodesoja.ui.fragment.estimate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.matreis.rendimentodesoja.App
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepositoryImp
import br.com.matreis.rendimentodesoja.data.repository.datasourceImp.LocalDatasourceImp
import br.com.matreis.rendimentodesoja.databinding.FragmentEstimatesBinding
import br.com.matreis.rendimentodesoja.ui.activity.NewSoybeanEstimateActivity
import br.com.matreis.rendimentodesoja.ui.activity.results.SoybeanYieldsResultsActivity
import br.com.matreis.rendimentodesoja.ui.adapter.BlocksFilterAdapter
import br.com.matreis.rendimentodesoja.ui.adapter.EstimateAdapter
import br.com.matreis.rendimentodesoja.ui.adapter.FarmsFilterAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EstimatesFragment : Fragment() {

    private lateinit var binding : FragmentEstimatesBinding
    private lateinit var adapterEstimates: EstimateAdapter
    private lateinit var adapterFarms: FarmsFilterAdapter
    private lateinit var adapterBlocks: BlocksFilterAdapter
    private lateinit var estimateViewModel: EstimateViewModel

    private var blockSelected = -1L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEstimatesBinding.inflate(inflater, container, false)
        estimateViewModel = ViewModelProvider(this, EstimateViewModel.EstimateViewModelFactory(
            SoybeanCalculatorRepositoryImp(
                LocalDatasourceImp(
                    (requireActivity().application as App).database.getFarmDao(),
                    (requireActivity().application as App).database.getBlockDao(),
                    (requireActivity().application as App).database.getEstimateDao()
                )
            )
        ))[EstimateViewModel::class.java]
        adapterEstimates = EstimateAdapter()
        adapterFarms = FarmsFilterAdapter()
        adapterBlocks = BlocksFilterAdapter()

        setUpRecyclerView()
        setUpListener()
        setUpAdapterListener()
        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        estimateViewModel.estimatesWithsamplingPoints.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                setEmptyLayoutVisibility(true)
            }else{
                setEmptyLayoutVisibility(false)
            }

            adapterEstimates.setEstimatesList(it)
        }
        estimateViewModel.getAllFarmsWithBlocks().observe(viewLifecycleOwner) {

            if(it.isEmpty()){
                binding.tvFarmFilter.visibility = View.GONE
                binding.tvBlockFilter.visibility = View.GONE

                binding.rvFrams.visibility = View.GONE
                binding.rvBlocks.visibility = View.GONE
            }else{
                binding.tvFarmFilter.visibility = View.VISIBLE
                //binding.rvBlocks.visibility = View.VISIBLE
                binding.rvFrams.visibility = View.VISIBLE
            }

            adapterFarms.setList(it)
        }
    }

    private fun setEmptyLayoutVisibility(isVisible: Boolean){
        if(isVisible){
            binding.llEmptyFarm.visibility = View.VISIBLE
            binding.rvEstimates.visibility = View.GONE
        }else{
            binding.llEmptyFarm.visibility = View.GONE
            binding.rvEstimates.visibility = View.VISIBLE
        }
    }

    private fun setUpAdapterListener() {
        adapterEstimates.listenClick = {
            val i = Intent(requireActivity(), SoybeanYieldsResultsActivity::class.java)
            i.putExtra("estimateId", it.estimate.id)
            startActivity(i)
        }
        adapterEstimates.listenDelete = {
            val builder = MaterialAlertDialogBuilder(requireActivity())
            builder.setTitle("Excluir estimativa")
            builder.setMessage("Deseja excluir essa estimativa?")
            builder.setPositiveButton("Sim") {p0, p1 ->
                if(blockSelected > 0){
                    estimateViewModel.deleteEstimateWithSamplingPoints(it.estimate, it.samplingPoints, true, blockSelected)
                }else{
                    estimateViewModel.deleteEstimateWithSamplingPoints(it.estimate, it.samplingPoints, false, null)
                }
            }
            builder.setNegativeButton("Não" , null)
            builder.show()
        }
        adapterFarms.listenClick = {

            if(it.isEmpty()){
                binding.tvBlockFilter.visibility = View.GONE
                binding.rvBlocks.visibility = View.GONE
            }else{
                binding.tvBlockFilter.visibility = View.VISIBLE
                binding.rvBlocks.visibility = View.VISIBLE
            }

            adapterBlocks.setList(it)
            Log.i("MYTAG", it.toString())
        }
        adapterFarms.listenCheck = {
            if(!it){
                binding.tvBlockFilter.visibility = View.GONE
                binding.rvBlocks.visibility = View.GONE
                estimateViewModel.getAllEstimateWithSamplingPoints()
                blockSelected = -1
            }
        }
        adapterBlocks.listenClick = {
            estimateViewModel.getAllEstimateByBlockId(it)
            blockSelected = it
            Log.i("MYTAG", it.toString())
        }
    }

    private fun setUpRecyclerView() {
        binding.rvEstimates.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterEstimates
        }
        binding.rvFrams.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterFarms
        }
        binding.rvBlocks.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterBlocks
        }
    }

    private fun setUpListener() {
        binding.fabNewEstimate.setOnClickListener {
            startActivity(Intent(requireActivity(), NewSoybeanEstimateActivity::class.java))
        }
    }


}