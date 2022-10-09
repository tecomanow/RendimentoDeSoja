package br.com.matreis.rendimentodesoja.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.matreis.rendimentodesoja.databinding.FragmentFarmsBlocksBinding
import br.com.matreis.rendimentodesoja.ui.adapter.ViewPagerAdapter
import br.com.matreis.rendimentodesoja.ui.fragment.blocks.BlocksFragment
import br.com.matreis.rendimentodesoja.ui.fragment.farms.FarmsFragment

class FarmsBlocksFragment : Fragment() {

    private lateinit var binding : FragmentFarmsBlocksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFarmsBlocksBinding.inflate(layoutInflater, container, false)

        setUpTabLayoutAndViewPager()

        return binding.root
    }

    private fun setUpTabLayoutAndViewPager() {

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FarmsFragment(), "Fazendas")
        adapter.addFragment(BlocksFragment(), "Quadras")

        binding.apply {
            viewPagerFarmsBlocks.adapter = adapter
            tabLayout.elevation = 0.0F
            tabLayout.setupWithViewPager(viewPagerFarmsBlocks)
        }
    }

}