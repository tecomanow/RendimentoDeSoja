package br.com.matreis.rendimentodesoja.ui.fragment.blocks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.matreis.rendimentodesoja.App
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.model.FarmWithBlock
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepositoryImp
import br.com.matreis.rendimentodesoja.data.repository.datasourceImp.LocalDatasourceImp
import br.com.matreis.rendimentodesoja.databinding.DialogEditBlockBinding
import br.com.matreis.rendimentodesoja.databinding.FragmentBlocksBinding
import br.com.matreis.rendimentodesoja.ui.activity.newblock.NewBlockActivity
import br.com.matreis.rendimentodesoja.ui.adapter.FarmWithBlocksAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BlocksFragment : Fragment() {

    private lateinit var binding : FragmentBlocksBinding
    private lateinit var farmWithBlocksAdapter: FarmWithBlocksAdapter
    private lateinit var blocksFragmentViewModel: BlocksFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlocksBinding.inflate(inflater, container, false)
        farmWithBlocksAdapter = FarmWithBlocksAdapter(requireActivity())
        blocksFragmentViewModel = ViewModelProvider(this,BlocksFragmentViewModel.BlocksFragmentViewModelFactory(
            SoybeanCalculatorRepositoryImp(
                LocalDatasourceImp(
                    (requireActivity().application as App).database.getFarmDao(),
                    (requireActivity().application as App).database.getBlockDao(),
                    (requireActivity().application as App).database.getEstimateDao()
                )
            )
        ))[BlocksFragmentViewModel::class.java]

        setUpRecyclerView()
        setUpListener()
        setUpObserver()
        setUpAdapterListen()


        return binding.root
    }

    private fun setUpAdapterListen() {
        farmWithBlocksAdapter.listenEdit = {
            openDialogEditBlock(it)
        }

        farmWithBlocksAdapter.listenDelete = {
            val builder = MaterialAlertDialogBuilder(requireActivity())
            builder.setTitle("Deletar quadra")
            builder.setMessage("Tem certeza que deseja deletar essa quadra?")
            builder.setPositiveButton("Sim") { p0, p1 ->
                blocksFragmentViewModel.deleteBlock(it)
            }
            builder.setNegativeButton("Não", null)
            builder.show()
        }
    }

    private fun openDialogEditBlock(block: Block) {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        val binding = DialogEditBlockBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        val dialog = builder.show()
        binding.apply {
            editTextBlockName.setText(block.blockName)
            editTextBlockSize.setText(block.size.toString())

            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            btnSave.setOnClickListener {
                val blockName = editTextBlockName.text.toString()
                val size = editTextBlockSize.text.toString()

                if(blockName.isNotBlank() && size.isNotBlank()){
                    block.blockName = blockName
                    block.size = size.toDouble()

                    blocksFragmentViewModel.updateBlock(block)
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setUpObserver() {
        blocksFragmentViewModel.getAllFarmsAndBlocks().observe(viewLifecycleOwner) {

            val newList = ArrayList<FarmWithBlock>()
            it.forEach {
                if(it.blocks.isNotEmpty()){
                    newList.add(it)
                }
            }

            if(newList.isEmpty()){
                setEmptyLayoutVisibility(true)
            }else{
                setEmptyLayoutVisibility(false)
            }

            farmWithBlocksAdapter.setFarmWithBlocksList(newList)
        }
    }

    private fun setEmptyLayoutVisibility(visible: Boolean){
        if(visible){
            binding.llEmptyBlock.visibility = View.VISIBLE
            binding.rvBlocks.visibility = View.GONE
        }else{
            binding.llEmptyBlock.visibility = View.GONE
            binding.rvBlocks.visibility = View.VISIBLE
        }
    }

    private fun setUpRecyclerView() {
        binding.rvBlocks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = farmWithBlocksAdapter
        }
    }

    private fun setUpListener() {
        binding.fabAddNewBlock.setOnClickListener {
            startActivity(Intent(requireActivity(), NewBlockActivity::class.java))
        }
    }


}