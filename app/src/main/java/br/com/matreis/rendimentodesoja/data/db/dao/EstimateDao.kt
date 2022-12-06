package br.com.matreis.rendimentodesoja.data.db.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.matreis.rendimentodesoja.data.model.Estimate
import br.com.matreis.rendimentodesoja.data.model.EstimateWithSamplingPoint
import br.com.matreis.rendimentodesoja.data.model.SamplingPoint

@Dao
interface EstimateDao {

    @Transaction
    suspend fun saveEstimate(estimateWithSamplingPoint: EstimateWithSamplingPoint) : Long {
        val id = insertEstimate(estimateWithSamplingPoint.estimate)
        estimateWithSamplingPoint.samplingPoints.forEach {
            it.idEstimate = id
            insertSamplingPoint(it)
        }
        return id
    }

    @Insert
    suspend fun insertEstimate(estimate: Estimate) : Long

    @Insert
    suspend fun insertSamplingPoint(samplingPoint: SamplingPoint)

    @Delete
    suspend fun deleteEstimate(estimate: Estimate)

    @Query("SELECT * FROM estimate WHERE id = :estimateId")
    fun getEstimateWithSamplingPointById(estimateId: Long) : EstimateWithSamplingPoint

    @Query("SELECT * FROM estimate")
    fun getAllEstimateWithSamplingPoints() : List<EstimateWithSamplingPoint>

    @Query("SELECT * FROM estimate WHERE idBlock = :blockId")
    fun getAllEstimateByBlockId(blockId: Long): List<EstimateWithSamplingPoint>

    @Delete
    suspend fun deleteSamplingPoint(samplingPoint: SamplingPoint)

    @Transaction
    @Delete
    suspend fun deleteEstimateWithSamplingPoints(estimate: Estimate, samplingPoints: List<SamplingPoint>){
        deleteEstimate(estimate)
        samplingPoints.forEach {
            deleteSamplingPoint(it)
        }
    }


}