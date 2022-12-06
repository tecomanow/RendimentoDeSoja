package br.com.matreis.rendimentodesoja.data.db

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.matreis.rendimentodesoja.data.db.dao.BlockDao
import br.com.matreis.rendimentodesoja.data.db.dao.EstimateDao
import br.com.matreis.rendimentodesoja.data.db.dao.FarmDao
import br.com.matreis.rendimentodesoja.data.model.*
import com.google.common.truth.Truth
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EstimateDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var blockDao: BlockDao
    private lateinit var farmDao: FarmDao
    private lateinit var estimateDao: EstimateDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()

        farmDao  = database.getFarmDao()
        blockDao = database.getBlockDao()
        estimateDao = database.getEstimateDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveAngGetEstimateWithSamplingPoint() {

        val farm = Farm(
            1,
            "Farm 1",
            "City 1"
        )

        CoroutineScope(Dispatchers.IO).launch {
            farmDao.insert(farm)
        }

        val block = Block(1,1,"Block 1",15.0,0)

        CoroutineScope(Dispatchers.IO).launch {
            blockDao.insertBlock(block)
        }

        val estimate = Estimate(1,1,"",0.5,1,
            1.0,0.15,0,"")
        val samplingPoint = SamplingPoint(0,1,0,1738,0)

        val samplingPoints = ArrayList<SamplingPoint>();
        samplingPoints.add(samplingPoint)

        val estimateWithSamplingPoint = EstimateWithSamplingPoint(estimate, samplingPoints)

        CoroutineScope(Dispatchers.IO).launch {
            val i = estimateDao.saveEstimate(estimateWithSamplingPoint)
            val estimateWithSamplingPointFromDb = estimateDao.getEstimateWithSamplingPointById(1)
            Truth.assertThat(estimateWithSamplingPointFromDb).isEqualTo(estimateWithSamplingPoint)
        }

    }

    @Test
    fun deleteEstimateWithSamplingPoint(){

        val farm = Farm(
            1,
            "Farm 1",
            "City 1"
        )

        CoroutineScope(Dispatchers.IO).launch {
            farmDao.insert(farm)
        }

        val block = Block(1,1,"Block 1",15.0,0)

        CoroutineScope(Dispatchers.IO).launch {
            blockDao.insertBlock(block)
        }

        val estimate = Estimate(1,1,"",0.5,1,
            1.0,0.15,0,"")
        val samplingPoint = SamplingPoint(0,1,0,1738,0)

        val samplingPoints = ArrayList<SamplingPoint>();
        samplingPoints.add(samplingPoint)

        val estimateWithSamplingPoint = EstimateWithSamplingPoint(estimate, samplingPoints)

        CoroutineScope(Dispatchers.Main).launch {
            estimateDao.saveEstimate(estimateWithSamplingPoint)
            estimateDao.deleteEstimateWithSamplingPoints(estimate,samplingPoints)
            val estimateWithSamplingPointFromDb = estimateDao.getEstimateWithSamplingPointById(1)
            Truth.assertThat(estimateWithSamplingPointFromDb).isNull()
        }

    }

}