package br.com.matreis.rendimentodesoja.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.model.FarmWithBlock
import br.com.matreis.rendimentodesoja.databinding.AdapterBlockBinding

class FarmWithBlocksAdapter(private val context: Context): RecyclerView.Adapter<FarmWithBlocksAdapter.MyFarmWithBlocksViewHolder>() {

    private var farmWithBlocks = ArrayList<FarmWithBlock>()
    var listenEdit: (block: Block) -> Unit = {}
    var listenDelete: (block: Block) -> Unit = {}

    fun setFarmWithBlocksList(list: List<FarmWithBlock>){
        this.farmWithBlocks = ArrayList(list)
        notifyDataSetChanged()
    }

    inner class MyFarmWithBlocksViewHolder(private val binding: AdapterBlockBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(farmWithBlock: FarmWithBlock){
            binding.tvFarmName.text = farmWithBlock.farm.name
            val adapter = BlocksAdapter()
            adapter.setBlockList(farmWithBlock.blocks)

            adapter.listenEdit = {
                listenEdit(it)
            }

            adapter.listenDelete = {
                listenDelete(it)
            }

            binding.rvBlocksChild.adapter = adapter
            binding.rvBlocksChild.setHasFixedSize(true)
            binding.rvBlocksChild.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFarmWithBlocksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterBlockBinding.inflate(inflater, parent, false)
        return MyFarmWithBlocksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyFarmWithBlocksViewHolder, position: Int) {

        holder.bind(farmWithBlocks[position])

    }

    override fun getItemCount(): Int = farmWithBlocks.size

}