package br.com.matreis.rendimentodesoja.ui.activity.results

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.data.model.Estimate
import br.com.matreis.rendimentodesoja.data.model.EstimateWithSamplingPoint
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepository
import br.com.matreis.rendimentodesoja.helper.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class EstimateResultsViewModel(private val repository: SoybeanCalculatorRepository) : ViewModel() {

    private val _estimateWithSamplingPoint = MutableLiveData<EstimateWithSamplingPoint>()
    val estimateWithSamplingPoint: LiveData<EstimateWithSamplingPoint>
        get() = _estimateWithSamplingPoint

    //var convertResults: (rowSpacing: Double, weigh1000Grains: Double, samplingPointSize: Double, estimateResults: Double) -> Unit = {rowSpacing: Double, weigh1000Grains: Double, samplingPointSize: Double, estimateResults: Double ->  }
    //fun getEstimateWithSamplingPointsById(estimateId: Long) = repository.getEstimateWithSamplingPointById(estimateId)

    fun getFarmAndBlock(blockId: Long, farmId: Long) = repository.getFarmAndBlock(blockId, farmId)

    fun getBlockById(blockId: Long) = repository.getBlockById(blockId)

    fun getEstimateResult(): Double {

        val estimateWithSamplingPoint = estimateWithSamplingPoint.value!!

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

        Log.i("MYTAG", "ESPAÇAMENTO $rowSpacing")
        Log.i("MYTAG", "PESO 1.000G $thousandWeight")
        Log.i("MYTAG", "TAMANHO AMOSTRAL $samplingPointSize")
        Log.i("MYTAG", "NUM GRAOS $numGrains")
        Log.i("MYTAG", "NUM MERDIO GRAOS $averageNumberGrains")
        //Log.i("MYTAG", "RESULTADP $estimateResult")

        return (averageNumberGrains * 10 * thousandWeight) / (samplingPointSize * rowSpacing)
    }

    fun getEstimateWithSamplingPoints(estimateId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            val estimateWithSamplingPoint = repository.getEstimateWithSamplingPointById(estimateId)
            _estimateWithSamplingPoint.postValue(estimateWithSamplingPoint)
        }
    }

    class EstimateResultsViewModelFactory(private val repository: SoybeanCalculatorRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(EstimateResultsViewModel::class.java)){
                return EstimateResultsViewModel(repository) as T
            }
            return super.create(modelClass)
        }
    }
}