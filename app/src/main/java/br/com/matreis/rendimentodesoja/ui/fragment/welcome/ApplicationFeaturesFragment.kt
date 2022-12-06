package br.com.matreis.rendimentodesoja.ui.fragment.welcome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.databinding.FragmentAplicationFeaturesBinding
import br.com.matreis.rendimentodesoja.ui.activity.MainActivity

class ApplicationFeaturesFragment : Fragment() {

    private lateinit var binding : FragmentAplicationFeaturesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAplicationFeaturesBinding.inflate(inflater, container, false)

        setUpToolbar()
        setUpListeners()

        return binding.root
    }

    private fun setUpToolbar() {
        binding.toolbar.textToolbar.text = "O que eu posso fazer?"
    }

    private fun setUpListeners() {
        binding.btnContinue.setOnClickListener {
            it.findNavController().navigate(R.id.action_aplicationFeaturesFragment_to_helpFragment)
        }
    }

}