package br.com.matreis.rendimentodesoja.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.matreis.rendimentodesoja.data.db.dao.FarmDao
import br.com.matreis.rendimentodesoja.data.model.Farm
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FarmDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var farmDao: FarmDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        farmDao  = database.getFarmDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun saveAndGetFarmTest() = runBlocking {
        val farms = listOf(
            Farm(
                1,
                "Farm 1",
                "City 1"
            ),
            Farm(
                2,
                "Farm 2",
                "City 2"
            ),
            Farm(
                3,
                "Farm 3",
                "City 3"
            )
        )
        farms.forEach {
            farmDao.insert(it)
        }

        val allFarmsFromDb = farmDao.getAllFarms().getOrAwaitValue()
        Truth.assertThat(allFarmsFromDb).isEqualTo(farms)

    }

    @Test
    fun deleteFarmTest() = runBlocking {
        val farms = listOf(
            Farm(
                1,
                "Farm 1",
                "City 1"
            ),
            Farm(
                2,
                "Farm 2",
                "City 2"
            ),
            Farm(
                3,
                "Farm 3",
                "City 3"
            )
        )
        farms.forEach {
            farmDao.insert(it)
        }

        farms.forEach {
            farmDao.delete(it)
        }

        val allFarmsFromDb = farmDao.getAllFarms().getOrAwaitValue()
        Truth.assertThat(allFarmsFromDb).isEmpty()

    }

}