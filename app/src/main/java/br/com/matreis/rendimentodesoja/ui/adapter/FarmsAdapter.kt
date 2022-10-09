package br.com.matreis.rendimentodesoja.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.databinding.AdapterFarmBinding

class FarmsAdapter : RecyclerView.Adapter<FarmsAdapter.MyFarmsViewHolder>(){

    private var farmList = ArrayList<Farm>()
    var listenerEdit : (farm: Farm) -> Unit = {}
    var listenerDelete : (farm: Farm) -> Unit = {}

    fun setList(farms: List<Farm>){
        farmList = ArrayList(farms)
        notifyDataSetChanged()
    }

    inner class MyFarmsViewHolder(private val binding: AdapterFarmBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(farm: Farm){

            binding.apply {
                tvFarmName.text = farm.name
                tvFarmCity.text = farm.city

                btnEditFarm.setOnClickListener {
                    listenerEdit(farm)
                }

                btnRemoveFarm.setOnClickListener {
                    listenerDelete(farm)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFarmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterFarmBinding.inflate(inflater, parent, false)
        return MyFarmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyFarmsViewHolder, position: Int) {
        holder.bind(farmList[position])
    }

    override fun getItemCount(): Int = farmList.size

}