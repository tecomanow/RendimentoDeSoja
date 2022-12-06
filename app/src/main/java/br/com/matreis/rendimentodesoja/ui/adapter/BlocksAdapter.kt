package br.com.matreis.rendimentodesoja.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.databinding.AdapterSubitemBlockBinding

class BlocksAdapter: RecyclerView.Adapter<BlocksAdapter.MyBlocksViewHolder>() {

    private var blocksList = ArrayList<Block>()
    var listenEdit: (block: Block) -> Unit = {}
    var listenDelete: (block: Block) -> Unit = {}

    fun setBlockList(list: List<Block>){
        this.blocksList = ArrayList(list)
        notifyDataSetChanged()
    }

    inner class MyBlocksViewHolder(private val binding: AdapterSubitemBlockBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(block: Block){
            binding.apply {
                tvBlockName.text = block.blockName
                tvBlockSize.text = "${block.size}"

                btnEdit.setOnClickListener {
                    listenEdit(block)
                }

                btnDelete.setOnClickListener {
                    listenDelete(block)
                }

                when(block.measurementSystem){
                    0 -> binding.tvTitleBlockName.text = "Tamanho da quadra (ha)"
                    1 -> binding.tvTitleBlockName.text = "Tamanho da quadra (acre)"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBlocksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterSubitemBlockBinding.inflate(inflater, parent, false)
        return MyBlocksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyBlocksViewHolder, position: Int) {
        holder.bind(blocksList[position])
    }

    override fun getItemCount(): Int = blocksList.size
}