package br.com.matreis.rendimentodesoja.ui.fragment.welcome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.databinding.FragmentMeasurementSystemBinding
import br.com.matreis.rendimentodesoja.ui.activity.WelcomeActivity
import com.google.android.material.snackbar.Snackbar

class MeasurementSystemFragment : Fragment() {

    private lateinit var binding : FragmentMeasurementSystemBinding
    private lateinit var measurementFragmentViewModel: MeasurementFragmentViewModel
    private var isEnableToContinue: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeasurementSystemBinding.inflate(inflater, container, false)
        measurementFragmentViewModel = ViewModelProvider(this)[MeasurementFragmentViewModel::class.java]

        setUpListeners()
        setUpLayout()

        return binding.root
    }

    private fun setUpLayout() {
        when(measurementFragmentViewModel.getMeasurementSystem(requireActivity())){
            0 -> {
                binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_unselected)
                binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_selected)
                isEnableToContinue = true
            }

            1 -> {
                binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_selected)
                binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_unselected)
                isEnableToContinue = true
            }

            else -> {
                binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_unselected)
                binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_unselected)
            }
        }
    }

    private fun setUpListeners() {
        binding.btnContinue.setOnClickListener {
            if(isEnableToContinue){
                it.findNavController().navigate(R.id.action_measurementSystemFragment_to_aplicationFeaturesFragment)
            }else{
                Snackbar.make(binding.root, "Escolha um sistema de medidas", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.llMetricSystem.setOnClickListener {
            binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_unselected)
            binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_selected)
            measurementFragmentViewModel.setMeasurementSystem(requireActivity(), 0)
            isEnableToContinue = true
        }

        binding.llImperialSystem.setOnClickListener {
            binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_selected)
            binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_unselected)
            measurementFragmentViewModel.setMeasurementSystem(requireActivity(), 1)
            isEnableToContinue = true
        }
    }

}