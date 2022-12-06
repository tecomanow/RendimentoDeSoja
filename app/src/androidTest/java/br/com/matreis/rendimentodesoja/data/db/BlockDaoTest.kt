package br.com.matreis.rendimentodesoja.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.matreis.rendimentodesoja.data.db.dao.BlockDao
import br.com.matreis.rendimentodesoja.data.db.dao.FarmDao
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.model.Farm
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BlockDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var blockDao: BlockDao
    private lateinit var farmDao: FarmDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        farmDao  = database.getFarmDao()
        blockDao = database.getBlockDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveAndGetBlockTest() = runBlocking {
        val farm = Farm(
            1,
            "Farm 1",
            "City 1"
        )

        farmDao.insert(farm)

        val blocks = listOf(
            Block(1,1,"Block 1",15.0,0),
            Block(2,1,"Block 2",20.0,0),
            Block(3,1,"Block 3",25.0,0)
        )
        blocks.forEach {
            blockDao.insertBlock(it)
        }
        val allBlocksFromDb = blockDao.getAllBlocks().getOrAwaitValue()
        Truth.assertThat(allBlocksFromDb).isEqualTo(blocks)
    }

    @Test
    fun deleteBlockTest() = runBlocking {
        val farm = Farm(
            1,
            "Farm 1",
            "City 1"
        )

        farmDao.insert(farm)

        val blocks = listOf(
            Block(1,1,"Block 1",15.0,0),
            Block(2,1,"Block 2",20.0,0),
            Block(3,1,"Block 3",25.0,0)
        )
        blocks.forEach {
            blockDao.insertBlock(it)
        }

        blocks.forEach {
            blockDao.deleteBlock(it)
        }

        val allBlocksFromDb = blockDao.getAllBlocks().getOrAwaitValue()
        Truth.assertThat(allBlocksFromDb).isEmpty()
    }

}