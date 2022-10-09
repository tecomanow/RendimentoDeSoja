package br.com.matreis.rendimentodesoja.data.repository

import androidx.lifecycle.LiveData
import br.com.matreis.rendimentodesoja.data.model.*
import br.com.matreis.rendimentodesoja.data.repository.datasource.LocalDatasource

class SoybeanCalculatorRepositoryImp(private val localDatasource: LocalDatasource) : SoybeanCalculatorRepository {

    override suspend fun insertFarm(farm: Farm) = localDatasource.insertFarm(farm)
    override suspend fun deleteFarm(farm: Farm) = localDatasource.deleteFarm(farm)
    override suspend fun updateFarm(farm: Farm) = localDatasource.updateFarm(farm)
    override fun getAllFarms(): LiveData<List<Farm>> = localDatasource.getAllFarms()
    override fun getAllFarmsWithBlocks(): LiveData<List<FarmWithBlock>> = localDatasource.getAllFarmsWithBlocks()
    override fun getFarmAndBlock(blockId: Long, farmId: Long): LiveData<FarmAndBlock> = localDatasource.getFarmAndBlock(blockId, farmId)
    override fun getBlockById(blockId: Long): LiveData<Block> = localDatasource.getBlockById(blockId)

    override suspend fun insertBlock(block: Block) = localDatasource.insertBlock(block)
    override suspend fun deleteBlock(block: Block) = localDatasource.deleteBlock(block)
    override suspend fun updateBlock(block: Block) = localDatasource.updateBlock(block)
    override fun getAllBlocks(): LiveData<List<Block>> = localDatasource.getAllBlocks()

    override suspend fun saveEstimate(estimateWithSamplingPoint: EstimateWithSamplingPoint) : Long = localDatasource.saveEstimate(estimateWithSamplingPoint)
    override fun getEstimateWithSamplingPointById(estimateId: Long): EstimateWithSamplingPoint = localDatasource.getEstimateWithSamplingPointById(estimateId)
    override fun getAllEstimateWithSamplingPoints(): List<EstimateWithSamplingPoint> = localDatasource.getAllEstimateWithSamplingPoints()
    override fun getAllEstimateByBlockId(blockId: Long): List<EstimateWithSamplingPoint> = localDatasource.getAllEstimateByBlockId(blockId)
    override suspend fun deleteEstimateWithSamplingPoints(
        estimate: Estimate,
        samplingPoints: List<SamplingPoint>
    ) = localDatasource.deleteEstimateWithSamplingPoints(estimate, samplingPoints)

}