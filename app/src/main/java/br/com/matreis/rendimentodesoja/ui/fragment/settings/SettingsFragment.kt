package br.com.matreis.rendimentodesoja.ui.fragment.settings

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.databinding.FragmentSettingsBinding
import br.com.matreis.rendimentodesoja.ui.fragment.welcome.MeasurementFragmentViewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        setUpListener()
        setUpLayout()

        return binding.root
    }

    private fun setUpLayout() {
        when(settingsViewModel.getMeasurementSystem(requireActivity())){
            0 -> {
                binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_unselected)
                binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_selected)
            }

            1 -> {
                binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_selected)
                binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_unselected)
            }

            else -> {
                binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_unselected)
                binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_unselected)
            }
        }
    }

    private fun setUpListener() {
        binding.apply {

            binding.llMetricSystem.setOnClickListener {
                val build = AlertDialog.Builder(requireActivity())
                build.setTitle("Alterar sistema de medidas")
                build.setMessage("Deseja alterar o sistema de medidas? Isso pode ocasionar conflitos com estimativas que já foram realizadas.")
                build.setPositiveButton("Sim") { dialogInterface, i ->
                    binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_unselected)
                    binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_selected)
                    settingsViewModel.setMeasurementSystem(requireActivity(), 0)
                }
                build.setNegativeButton("Não", null)
                build.show()
            }

            binding.llImperialSystem.setOnClickListener {
                val build = AlertDialog.Builder(requireActivity())
                build.setTitle("Alterar sistema de medidas")
                build.setMessage("Deseja alterar o sistema de medidas? Isso pode ocasionar conflitos com estimativas já feitas.")
                build.setPositiveButton("Sim") { dialogInterface, i ->
                    binding.llImperialSystem.setBackgroundResource(R.drawable.stroke_selected)
                    binding.llMetricSystem.setBackgroundResource(R.drawable.stroke_unselected)
                    settingsViewModel.setMeasurementSystem(requireActivity(), 1)
                }
                build.setNegativeButton("Não", null)
                build.show()
            }

        }
    }


}