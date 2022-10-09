package br.com.matreis.rendimentodesoja.ui.fragment.welcome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.matreis.rendimentodesoja.databinding.FragmentAplicationFeaturesBinding
import br.com.matreis.rendimentodesoja.ui.activity.MainActivity

class AplicationFeaturesFragment : Fragment() {

    private lateinit var binding : FragmentAplicationFeaturesBinding
    private lateinit var measurementFragmentViewModel: MeasurementFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAplicationFeaturesBinding.inflate(inflater, container, false)
        measurementFragmentViewModel = ViewModelProvider(this)[MeasurementFragmentViewModel::class.java]

        setUpListeners()

        return binding.root
    }

    private fun setUpListeners() {
        binding.btnUnderstood.setOnClickListener {
            measurementFragmentViewModel.setFirstRun(requireActivity())
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

}