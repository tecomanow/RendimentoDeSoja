package br.com.matreis.rendimentodesoja.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.data.model.EstimateWithSamplingPoint
import br.com.matreis.rendimentodesoja.databinding.AdapterEstimatesBinding
import br.com.matreis.rendimentodesoja.helper.convertEstimateToImperialSystem
import br.com.matreis.rendimentodesoja.helper.convertFeetToMeters
import br.com.matreis.rendimentodesoja.helper.convertInchesToMeters
import br.com.matreis.rendimentodesoja.helper.convertLbToKg
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class EstimateAdapter: RecyclerView.Adapter<EstimateAdapter.MyEstimateViewHolder>() {

    var listenClick: (estimateWithSamplingPoints: EstimateWithSamplingPoint) -> Unit = {}
    var listenDelete: (estimateWithSamplingPoints: EstimateWithSamplingPoint) -> Unit = {}

    private var numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale("pt","BR"))
    private var numberFormatEua: NumberFormat = NumberFormat.getNumberInstance(Locale.US)

    private var estimatesList: List<EstimateWithSamplingPoint> = ArrayList()
    fun setEstimatesList(list: List<EstimateWithSamplingPoint>){
        this.estimatesList = list
        notifyDataSetChanged()
    }

    inner class MyEstimateViewHolder(private val binding: AdapterEstimatesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(estimateWithSamplingPoint: EstimateWithSamplingPoint){
            binding.apply {
                textViewDateEstimate.text = estimateWithSamplingPoint.estimate.date
                textViewEstimateCode.text = "Estimativa SBC#${estimateWithSamplingPoint.estimate.id}"
                textViewEstimateProduction.text = "Estimado ${getEstimateResult(estimateWithSamplingPoint)}"
                textViewEstimateDescription.text = estimateWithSamplingPoint.estimate.description
                tvMeasurementSystemUsed.text = if(estimateWithSamplingPoint.estimate.measurementSystem == 1 ) "Sistema Imperial" else "Sistema métrico"

                binding.root.setOnClickListener {
                    listenClick(estimateWithSamplingPoint)
                }

                binding.btnRemoveEstimate.setOnClickListener {
                    listenDelete(estimateWithSamplingPoint)
                }
            }
        }

        private fun getEstimateResult(estimateWithSamplingPoint: EstimateWithSamplingPoint): String {
            var rowSpacing = estimateWithSamplingPoint.estimate.rowSpacing
            var thousandWeight = estimateWithSamplingPoint.estimate.thousandGrainWeight
            var samplingPointSize = estimateWithSamplingPoint.estimate.sizeSamplingPoint
            var averageNumberGrains = 0.0
            var numGrains = 0

            if (estimateWithSamplingPoint.estimate.measurementSystem == 1) {
                rowSpacing = rowSpacing.convertInchesToMeters()
                thousandWeight = thousandWeight.convertLbToKg()
                samplingPointSize = samplingPointSize.convertFeetToMeters()
            }

            estimateWithSamplingPoint.samplingPoints.forEach {
                numGrains += it.numberSeeds
            }

            averageNumberGrains = (numGrains / estimateWithSamplingPoint.samplingPoints.size).toDouble()

            /*Log.i("MYTAG", "ESPAÇAMENTO $rowSpacing")
            Log.i("MYTAG", "PESO 1.000G $thousandWeight")
            Log.i("MYTAG", "TAMANHO AMOSTRAL $samplingPointSize")
            Log.i("MYTAG", "NUM GRAOS $numGrains")
            Log.i("MYTAG", "NUM MERDIO GRAOS $averageNumberGrains")
            Log.i("MYTAG", "RESULTADP $estimateResult")*/

            var results = (averageNumberGrains * 10 * thousandWeight) / (samplingPointSize * rowSpacing)
            //var resultsString = results.toString()

            if(estimateWithSamplingPoint.estimate.measurementSystem == 1){
                results = results.convertEstimateToImperialSystem()
                return "${numberFormatEua.format(results)} ${binding.root.context.resources.getString(R.string.bushels_acre)}"
                //resultsString = results.toString()
                //resultsString += " ${binding.root.context.resources.getString(R.string.bushels_acre)}"
            }else{
                //resultsString += " ${binding.root.context.resources.getString(R.string.kg_hectares)}"
                return "${numberFormat.format(results)} ${binding.root.context.resources.getString(R.string.kg_hectares)}"
            }

            //return
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEstimateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterEstimatesBinding.inflate(inflater, parent, false)
        return MyEstimateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyEstimateViewHolder, position: Int) {
        holder.bind(estimatesList[position])
    }

    override fun getItemCount(): Int = estimatesList.size

}