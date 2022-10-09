package br.com.matreis.rendimentodesoja.data.repository.datasource

import androidx.lifecycle.LiveData
import br.com.matreis.rendimentodesoja.data.db.dao.FarmDao
import br.com.matreis.rendimentodesoja.data.model.*

interface LocalDatasource {

    suspend fun insertFarm(farm: Farm)
    suspend fun deleteFarm(farm: Farm)
    suspend fun updateFarm(farm: Farm)
    fun getAllFarms(): LiveData<List<Farm>>
    fun getAllFarmsWithBlocks(): LiveData<List<FarmWithBlock>>
    fun getFarmAndBlock(blockId: Long, farmId: Long) : LiveData<FarmAndBlock>

    suspend fun insertBlock(block: Block)
    suspend fun deleteBlock(block: Block)
    suspend fun updateBlock(block: Block)
    fun getAllBlocks(): LiveData<List<Block>>
    fun getBlockById(blockId: Long): LiveData<Block>

    suspend fun saveEstimate(estimateWithSamplingPoint: EstimateWithSamplingPoint) : Long
    fun getEstimateWithSamplingPointById(estimateId: Long) : EstimateWithSamplingPoint
    fun getAllEstimateWithSamplingPoints() : List<EstimateWithSamplingPoint>
    fun getAllEstimateByBlockId(blockId: Long): List<EstimateWithSamplingPoint>
    suspend fun deleteEstimateWithSamplingPoints(estimate: Estimate, samplingPoints: List<SamplingPoint>)

}