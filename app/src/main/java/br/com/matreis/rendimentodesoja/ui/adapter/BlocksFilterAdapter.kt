package br.com.matreis.rendimentodesoja.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.data.model.FarmWithBlock
import br.com.matreis.rendimentodesoja.databinding.AdapterBlockFilterBinding
import br.com.matreis.rendimentodesoja.databinding.AdapterFarmFilterBinding

class BlocksFilterAdapter: RecyclerView.Adapter<BlocksFilterAdapter.MyBlcoksFilterViewHolder>() {

    var listenClick:(blockId: Long) -> Unit = {}
    private var lastCheckedPosition: Int = -1

    private var list = ArrayList<Block>()
    fun setList(Blocks: List<Block>){
        list = ArrayList(Blocks)
        lastCheckedPosition = -1
        notifyDataSetChanged()
    }

    inner class MyBlcoksFilterViewHolder(private val binding: AdapterBlockFilterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(block: Block){
            binding.tvBlockName.setOnClickListener {

                val copyOfLastCheckedPosition = lastCheckedPosition;
                lastCheckedPosition = adapterPosition
                notifyItemChanged(copyOfLastCheckedPosition)
                notifyItemChanged(lastCheckedPosition)
                //listenCheck(true)
                listenClick(block.idBlock)
            }
            /*binding.root.setOnClickListener {
                listenClick(block.idBlock)
            }*/
            if(adapterPosition == lastCheckedPosition){
                binding.tgButtonFilter.check(binding.tvBlockName.id)
            }else{
                binding.tgButtonFilter.uncheck(binding.tvBlockName.id)
            }
            binding.tvBlockName.text = block.blockName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBlcoksFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterBlockFilterBinding.inflate(inflater, parent, false)
        return MyBlcoksFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyBlcoksFilterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}