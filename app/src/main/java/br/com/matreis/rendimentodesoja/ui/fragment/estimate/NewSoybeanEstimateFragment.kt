package br.com.matreis.rendimentodesoja.ui.fragment.estimate

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import br.com.matreis.rendimentodesoja.App
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.data.model.*
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepositoryImp
import br.com.matreis.rendimentodesoja.data.repository.datasourceImp.LocalDatasourceImp
import br.com.matreis.rendimentodesoja.databinding.FragmentNewSoybeanEstimateBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewSoybeanEstimateFragment : Fragment() {

    private lateinit var binding: FragmentNewSoybeanEstimateBinding
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

    private val farmsName = ArrayList<String>()
    private val blocksName = ArrayList<String>()
    private var farmsWithBlockList = ArrayList<FarmWithBlock>()
    private var blocksList = ArrayList<Block>()
    private var blockSelectedId : Long = -1L
    private var actualMeasurementSystem: Int? = null
    private var farmSelectedPosition: Int = -1
    private var blockSelectedPosition: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewSoybeanEstimateBinding.inflate(inflater, container, false)
        farmsWithBlockList.clear()
        farmsName.clear()
        blocksList.clear()
        blocksName.clear()
        blockSelectedId = -1

        farmsName.add("Selecione uma fazenda")
        estimateViewModel.getMeasurementSystem(requireActivity())
        setUpObservers()
        setUpListener()

        Log.i("MYTAG", "ON CREATE VIEW")

        return binding.root
    }

    private fun setUpObservers() {
        estimateViewModel.getAllFarmsWithBlocks().observe(viewLifecycleOwner) {

            setUpSpinners(it)
            //Log.i("MYTAG", it.toString())
        }
        estimateViewModel.measurementSystem.observe(viewLifecycleOwner) {

            actualMeasurementSystem = it

            if(it == 0){

                val samplingPointsSize = requireActivity().resources.getString(R.string.sampling_points_size) + " (m) "
                val rowSpacing = requireActivity().resources.getString(R.string.row_spacing) + " (m) "
                val thousandGrainsWeight = requireActivity().resources.getString(R.string.peso_mil_graos) + " (kg) "

                binding.textViewSamplingPointsSize.text = samplingPointsSize
                binding.textViewRowSpacing.text = rowSpacing
                binding.textView1000Grains.text = thousandGrainsWeight

            }else if(it == 1){

                val samplingPointsSize = requireActivity().resources.getString(R.string.sampling_points_size) + " (pés) " // "foot" em inglês
                val rowSpacing = requireActivity().resources.getString(R.string.row_spacing) + " (pol) " // "in" em inglês
                val thousandGrainsWeight = requireActivity().resources.getString(R.string.peso_mil_graos) + " (lb) "

                binding.textViewSamplingPointsSize.text = samplingPointsSize
                binding.textViewRowSpacing.text = rowSpacing
                binding.textView1000Grains.text = thousandGrainsWeight

            }
        }
    }

    private fun setUpSpinners(list: List<FarmWithBlock>) {

        list.forEach {
            farmsName.add(it.farm.name)
        }

        ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, farmsName).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerFarm.adapter = adapter
        }

        farmsWithBlockList = ArrayList(list)

    }

    private fun setUpListener() {
        binding.btnContinue.setOnClickListener {
            if(checkInputs()){

                val date = getDateFormatted(System.currentTimeMillis())
                val rowSpacing = binding.editTextRowSpacing.text.toString().toDouble()
                val numberSamplingPoint = binding.editTextSamplingPoints.text.toString().toInt()
                val samplingPointsSizes = binding.editTextSamplingPointsSize.text.toString().toDouble()
                val thousandGrainWeight = binding.editText1000Grains.text.toString().toDouble()
                val description = "Faz. ${farmsWithBlockList[farmSelectedPosition].farm.name} - Qua. ${blocksList[blockSelectedPosition].blockName}"

                val estimate = Estimate(
                    0L,
                    blockSelectedId?:0L,
                    date,
                    rowSpacing,
                    numberSamplingPoint,
                    samplingPointsSizes,
                    thousandGrainWeight,
                    actualMeasurementSystem!!,
                    description
                )

                estimateViewModel.setEstimate(estimate)

                it.findNavController().navigate(R.id.action_newSoybeanEstimateFragment_to_estimateDataCollectFragment)
            }
        }

        binding.spinnerFarm.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                blocksName.clear()
                blocksList.clear()

                if(p2 > 0){
                    val farmSelected = farmsWithBlockList[p2-1]
                    farmSelectedPosition = p2-1
                    blocksName.add("Selecione uma quadra")
                    farmSelected.blocks.forEach {
                        blocksName.add(it.blockName)
                    }

                    blocksList = ArrayList(farmSelected.blocks)

                    ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, blocksName).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerBlock.adapter = adapter
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        binding.spinnerBlock.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 > 0){
                    val blockSelected = blocksList[p2-1]
                    blockSelectedPosition = p2-1
                    blockSelectedId = blockSelected.idBlock
                    Log.i("MYTAG", "BLOCK SELECTED: ${blockSelected.blockName}")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun getDateFormatted(currentTimeMillis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("Pt", "BR"))
        return formatter.format(currentTimeMillis)
    }

    private fun checkInputs(): Boolean {
        val rowSpacing = binding.editTextRowSpacing.text.toString()
        val numberSamplingPoint = binding.editTextSamplingPoints.text.toString()
        val samplingPointsSizes = binding.editTextSamplingPointsSize.text.toString()
        val weight1000Grains = binding.editText1000Grains.text.toString()

        return if(numberSamplingPoint.isNotBlank() && numberSamplingPoint.toDouble() > 0){
            if(samplingPointsSizes.isNotBlank() && samplingPointsSizes.toDouble() > 0){
                if(rowSpacing.isNotBlank() && rowSpacing.toDouble() > 0){
                    if(weight1000Grains.isNotBlank() && weight1000Grains.toDouble() >0){
                        if(blockSelectedId > 0){
                            true
                        }else{
                            Snackbar.make(binding.root, "Escolha uma quadra", Snackbar.LENGTH_LONG).show()
                            false
                        }
                    }else{
                        binding.editText1000Grains.error = "Insira o peso"
                        false
                    }
                }else{
                    binding.editTextRowSpacing.error = "Insira o espaçamento entre fileiras"
                    false
                }
            }else{
                binding.editTextSamplingPointsSize.error = "Insira o tamanho dos pontos amostrais"
                false
            }
        }else{
            binding.editTextSamplingPoints.error = "Insira o número de pontos amostrais"
            false
        }
    }

    override fun onResume() {
        super.onResume()
    }

}