package br.com.matreis.rendimentodesoja.data.repository.datasourceImp

import androidx.lifecycle.LiveData
import br.com.matreis.rendimentodesoja.data.db.dao.BlockDao
import br.com.matreis.rendimentodesoja.data.db.dao.EstimateDao
import br.com.matreis.rendimentodesoja.data.db.dao.FarmDao
import br.com.matreis.rendimentodesoja.data.model.*
import br.com.matreis.rendimentodesoja.data.repository.datasource.LocalDatasource

class LocalDatasourceImp(private val farmDao: FarmDao, private val blockDao: BlockDao, private val estimateDao: EstimateDao): LocalDatasource {

    override suspend fun insertFarm(farm: Farm) = farmDao.insert(farm)
    override suspend fun deleteFarm(farm: Farm) = farmDao.delete(farm)
    override suspend fun updateFarm(farm: Farm) = farmDao.update(farm)
    override fun getAllFarms(): LiveData<List<Farm>> = farmDao.getAllFarms()
    override fun getAllFarmsWithBlocks(): LiveData<List<FarmWithBlock>> = farmDao.getAllFarmsWithBlocks()
    override fun getFarmAndBlock(blockId: Long, farmId: Long): LiveData<FarmAndBlock> = farmDao.getFarmAndBlock(blockId, farmId)

    override suspend fun insertBlock(block: Block) = blockDao.insertBlock(block)
    override suspend fun deleteBlock(block: Block) = blockDao.deleteBlock(block)
    override suspend fun updateBlock(block: Block) = blockDao.updateBlock(block)
    override fun getAllBlocks(): LiveData<List<Block>> = blockDao.getAllBlocks()
    override fun getBlockById(blockId: Long): LiveData<Block> = blockDao.getBlockById(blockId)

    override suspend fun saveEstimate(estimateWithSamplingPoint: EstimateWithSamplingPoint) : Long = estimateDao.saveEstimate(estimateWithSamplingPoint)
    override fun getEstimateWithSamplingPointById(estimateId: Long): EstimateWithSamplingPoint = estimateDao.getEstimateWithSamplingPointById(estimateId)
    override fun getAllEstimateWithSamplingPoints(): List<EstimateWithSamplingPoint> = estimateDao.getAllEstimateWithSamplingPoints()
    override fun getAllEstimateByBlockId(blockId: Long): List<EstimateWithSamplingPoint> = estimateDao.getAllEstimateByBlockId(blockId)

    override suspend fun deleteEstimateWithSamplingPoints(
        estimate: Estimate,
        samplingPoints: List<SamplingPoint>
    ) = estimateDao.deleteEstimateWithSamplingPoints(estimate, samplingPoints)

}