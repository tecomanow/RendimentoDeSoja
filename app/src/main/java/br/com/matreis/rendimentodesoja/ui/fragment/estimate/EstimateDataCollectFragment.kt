package br.com.matreis.rendimentodesoja.ui.fragment.estimate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.matreis.rendimentodesoja.App
import br.com.matreis.rendimentodesoja.data.model.SamplingPoint
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepositoryImp
import br.com.matreis.rendimentodesoja.data.repository.datasourceImp.LocalDatasourceImp
import br.com.matreis.rendimentodesoja.databinding.DialogDataCollectBinding
import br.com.matreis.rendimentodesoja.databinding.FragmentEstimateDataCollectBinding
import br.com.matreis.rendimentodesoja.helper.checkZeroOrValue
import br.com.matreis.rendimentodesoja.ui.activity.results.SoybeanYieldsResultsActivity
import br.com.matreis.rendimentodesoja.ui.adapter.SamplingPointsAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class EstimateDataCollectFragment : Fragment() {

    private lateinit var binding : FragmentEstimateDataCollectBinding
    private val estimateViewModel: EstimateViewModel by activityViewModels(
        factoryProducer = {
            EstimateViewModel.EstimateViewModelFactory(
                SoybeanCalculatorRepositoryImp(
                    LocalDatasourceImp(
                        (requireActivity().application as App).database.getFarmDao(),
                        (requireActivity().application as App).database.getBlockDao(),
                        (requireActivity().application as App).database.getEstimateDao()
                    )
                )
            )
        }
    )
    private val samplingPointsAdapter by lazy { SamplingPointsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEstimateDataCollectBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        setUpObervers()
        setUpListener()
        setUpAdapterListener()

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvSamplingPoints.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = samplingPointsAdapter
        }
    }

    private fun setUpAdapterListener() {
        samplingPointsAdapter.listenCollect = { it, pos ->
            Log.i("MYTAG", it.toString())
            openCollectDataDialog(it, pos)
        }
    }

    private fun openCollectDataDialog(point: SamplingPoint, pos: Int) {
        val build = MaterialAlertDialogBuilder(requireActivity())
        val binding = DialogDataCollectBinding.inflate(layoutInflater, null, false)
        build.setView(binding.root)
        val dialog = build.show()
        binding.apply {

            editTextNumberPlants.setText(point.numberPlants.checkZeroOrValue())
            editTextNumberPods.setText(point.numberPods.checkZeroOrValue())
            editTextNumberSeeds.setText(point.numberSeeds.checkZeroOrValue())

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                val numberPlants = editTextNumberPlants.text.toString().toInt()
                val numberPods = editTextNumberPods.text.toString().toInt()
                val numberSeeds = editTextNumberSeeds.text.toString().toInt()

                point.numberPlants = numberPlants
                point.numberPods = numberPods
                point.numberSeeds = numberSeeds

                estimateViewModel.updateSamplingPoint(point, pos)
                samplingPointsAdapter.notifyItemChanged(pos)
                dialog.dismiss()
            }
        }
    }

    private fun setUpObervers() {
        estimateViewModel.estimateWithSamplingPoint.observe(viewLifecycleOwner){
            samplingPointsAdapter.setSamplingPointList(it.samplingPoints)
            Log.i("MYTAG", it.samplingPoints.toString())
        }
        estimateViewModel.estimateId.observe(viewLifecycleOwner) {
            val i = Intent(requireActivity(), SoybeanYieldsResultsActivity::class.java)
            i.putExtra("estimateId", it)
            startActivity(i)
        }
    }

    private fun setUpListener() {
        binding.btnGetEstimate.setOnClickListener {
            estimateViewModel.saveEstimate()
        }
    }


}