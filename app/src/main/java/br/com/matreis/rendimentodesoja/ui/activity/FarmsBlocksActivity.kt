package br.com.matreis.rendimentodesoja.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.databinding.ActivityFarmsBlocksBinding
import br.com.matreis.rendimentodesoja.ui.adapter.ViewPagerAdapter
import br.com.matreis.rendimentodesoja.ui.fragment.blocks.BlocksFragment
import br.com.matreis.rendimentodesoja.ui.fragment.farms.FarmsFragment

class FarmsBlocksActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFarmsBlocksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFarmsBlocksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpCustomToolbar()
        setUpTabLayoutAndViewPager()

    }

    private fun setUpCustomToolbar() {
        binding.toolbar.include5.visibility = View.GONE
        binding.toolbar.textToolbar.text = getString(R.string.farms_blocks)
        binding.toolbar.imgBtnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpTabLayoutAndViewPager() {
        binding.apply {
            val adapter = ViewPagerAdapter(supportFragmentManager)
            adapter.addFragment(FarmsFragment(), "Fazendas")
            adapter.addFragment(BlocksFragment(), "Quadras")

            viewPagerFarmsBlocks.adapter = adapter
            tabLayout.elevation = 0.0F
            tabLayout.setupWithViewPager(viewPagerFarmsBlocks)
        }
    }
}