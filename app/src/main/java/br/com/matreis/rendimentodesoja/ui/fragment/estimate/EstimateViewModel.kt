package br.com.matreis.rendimentodesoja.ui.fragment.estimate

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import br.com.matreis.rendimentodesoja.data.model.Estimate
import br.com.matreis.rendimentodesoja.data.model.EstimateWithSamplingPoint
import br.com.matreis.rendimentodesoja.data.model.FarmWithBlock
import br.com.matreis.rendimentodesoja.data.model.SamplingPoint
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepository
import br.com.matreis.rendimentodesoja.helper.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstimateViewModel(private val repository: SoybeanCalculatorRepository) : ViewModel() {

    private val _estimatesWithSamplings = MutableLiveData<List<EstimateWithSamplingPoint>>()
    val estimatesWithsamplingPoints: LiveData<List<EstimateWithSamplingPoint>>
        get() = _estimatesWithSamplings

    //private var estimateWithSamplingPoint: EstimateWithSamplingPoint? = null
    private val estimateWithSamplingPointData = MutableLiveData<EstimateWithSamplingPoint>()
    private val _estimateId = MutableLiveData<Long>()
    private val _measurementSystem = MutableLiveData<Int>()



    val estimateWithSamplingPoint : LiveData<EstimateWithSamplingPoint>
        get() = estimateWithSamplingPointData

    val estimateId : LiveData<Long>
        get() = _estimateId

    val measurementSystem: LiveData<Int>
        get() = _measurementSystem

    init {
        getAllEstimateWithSamplingPoints()
    }

    fun setEstimate(estimate: Estimate){
        viewModelScope.launch {
            val listTemp = ArrayList<SamplingPoint>()
            /*for(i in 1..estimateWithSamplingPoint.estimate.numberSamplingPoint){
                estimateWithSamplingPoint.samplingPoints.add(SamplingPoint(0L,0,0,0,0))
            }*/
            for(i in 1..estimate.numberSamplingPoint){
                listTemp.add(SamplingPoint(0L,0,0,0,0))
            }
            val estimateWithSamplingPoint = EstimateWithSamplingPoint(estimate, listTemp)
            estimateWithSamplingPointData.postValue(estimateWithSamplingPoint)
        }
    }

    fun updateSamplingPoint(samplingPoint: SamplingPoint, position: Int){
        val listTemp = ArrayList<SamplingPoint>()
        estimateWithSamplingPointData.value?.let {
            listTemp.addAll(it.samplingPoints)
        }
        listTemp.set(position, samplingPoint)
        estimateWithSamplingPointData.value?.samplingPoints = listTemp
    }

    fun saveEstimate() {
        viewModelScope.launch(Dispatchers.IO) {
            estimateWithSamplingPointData.value?.let {
                val estimateId = repository.saveEstimate(it)
                _estimateId.postValue(estimateId)
            }
        }
    }

    fun getAllFarmsWithBlocks() = repository.getAllFarmsWithBlocks()

    fun getAllEstimateWithSamplingPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            _estimatesWithSamplings.postValue(repository.getAllEstimateWithSamplingPoints())
        }
    }

    fun getAllEstimateByBlockId(blockId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _estimatesWithSamplings.postValue(repository.getAllEstimateByBlockId(blockId))
        }
    }

    fun deleteEstimateWithSamplingPoints(estimate: Estimate, samplingPoints: List<SamplingPoint>, hasBlockSelected: Boolean, blockId: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEstimateWithSamplingPoints(estimate, samplingPoints)
            if(!hasBlockSelected){
                getAllEstimateWithSamplingPoints()
            }else{
                getAllEstimateByBlockId(blockId!!)
            }
        }
    }

    fun getMeasurementSystem(context: Context){
        val manager = PreferencesManager(context)
        _measurementSystem.value = manager.getMeasurementSystem()
    }

    class EstimateViewModelFactory(private val repository: SoybeanCalculatorRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if(modelClass.isAssignableFrom(EstimateViewModel::class.java)){
                EstimateViewModel(repository) as T
            }else{
                super.create(modelClass)
            }
        }
    }
}