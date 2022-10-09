package br.com.matreis.rendimentodesoja.ui.fragment.welcome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.databinding.FragmentWelcomeBinding
import br.com.matreis.rendimentodesoja.ui.activity.WelcomeActivity

class WelcomeFragment : Fragment() {

    private lateinit var binding : FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        initListener()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initListener() {
        binding.btnContinue.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment_to_measurementSystemFragment)
        }
    }

}