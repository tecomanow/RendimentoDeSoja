package br.com.matreis.rendimentodesoja.ui.fragment.welcome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.databinding.FragmentHelpBinding
import br.com.matreis.rendimentodesoja.ui.activity.MainActivity


class HelpFragment : Fragment() {

    private lateinit var binding: FragmentHelpBinding
    private lateinit var measurementFragmentViewModel: MeasurementFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        measurementFragmentViewModel = ViewModelProvider(this)[MeasurementFragmentViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpToolbar()
        initListeners()
    }

    private fun initListeners() {
        binding.btnUnderstood.setOnClickListener {
            measurementFragmentViewModel.setFirstRun(requireActivity())
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.textToolbar.text = "Ajuda"
    }

}