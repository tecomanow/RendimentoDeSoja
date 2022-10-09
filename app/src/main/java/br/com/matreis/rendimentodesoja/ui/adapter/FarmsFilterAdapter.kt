package br.com.matreis.rendimentodesoja.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.data.model.FarmWithBlock
import br.com.matreis.rendimentodesoja.databinding.AdapterFarmFilterBinding

class FarmsFilterAdapter: RecyclerView.Adapter<FarmsFilterAdapter.MyFarmsFilterViewHolder>() {

    var listenClick:(blocks: List<Block>) -> Unit = {}
    var listenCheck:(isChecked: Boolean) -> Unit = {}
    private var lastCheckedPosition: Int = -1

    private var list = ArrayList<FarmWithBlock>()
    fun setList(farmsWithBlocs: List<FarmWithBlock>){
        list = ArrayList(farmsWithBlocs)
        notifyDataSetChanged()
    }

    inner class MyFarmsFilterViewHolder(private val binding: AdapterFarmFilterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(farmsWithBlocs: FarmWithBlock){
            /*binding.root.setOnClickListener {
                listenClick(farmsWithBlocs.blocks)
            }*/
            binding.tvFarmName.setOnClickListener {

                if(adapterPosition == lastCheckedPosition){
                    Log.i("MYTAG FILTER", "POSITION == LASTCHECKED")
                    lastCheckedPosition = -1
                    listenCheck(false)
                }else{
                    val copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = adapterPosition
                    notifyItemChanged(copyOfLastCheckedPosition)
                    notifyItemChanged(lastCheckedPosition)
                    //listenCheck(true)
                    listenClick(farmsWithBlocs.blocks)
                }

            }

            if(adapterPosition == lastCheckedPosition){
                binding.tgButtonFilter.check(binding.tvFarmName.id)
            }else{
                binding.tgButtonFilter.uncheck(binding.tvFarmName.id)
            }
            binding.tvFarmName.text = farmsWithBlocs.farm.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFarmsFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterFarmFilterBinding.inflate(inflater, parent, false)
        return MyFarmsFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyFarmsFilterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}