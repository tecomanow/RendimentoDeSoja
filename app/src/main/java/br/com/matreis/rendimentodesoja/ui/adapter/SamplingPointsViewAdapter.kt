package br.com.matreis.rendimentodesoja.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.matreis.rendimentodesoja.data.model.SamplingPoint
import br.com.matreis.rendimentodesoja.databinding.AdapterSamplingPointCollectBinding
import br.com.matreis.rendimentodesoja.databinding.AdapterSamplingPointViewBinding

class SamplingPointsViewAdapter :
    RecyclerView.Adapter<SamplingPointsViewAdapter.MySamplingPointsViewHolder>() {

    private var samplingPointList : List<SamplingPoint> = ArrayList()

    fun setSamplingPointList(list: List<SamplingPoint>) {
        samplingPointList = list
        notifyDataSetChanged()
    }

    inner class MySamplingPointsViewHolder(private val binding: AdapterSamplingPointViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(samplingPoint: SamplingPoint) {
            binding.apply {
                textViewNumberBeansValue.text = samplingPoint.numberSeeds.toString()
                textViewNumberPlantsValue.text = samplingPoint.numberPlants.toString()
                textViewNumberPodsValue.text = samplingPoint.numberPods.toString()
                textViewSamplingPointNumber.text = "Ponto amostral - ${(adapterPosition + 1)}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySamplingPointsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterSamplingPointViewBinding.inflate(inflater, parent, false)
        return MySamplingPointsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MySamplingPointsViewHolder, position: Int) {
        holder.bind(samplingPointList[position])
    }

    override fun getItemCount(): Int = samplingPointList.size

}