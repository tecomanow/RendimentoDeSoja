package br.com.matreis.rendimentodesoja.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.data.model.FarmAndBlock
import br.com.matreis.rendimentodesoja.data.model.FarmWithBlock

@Dao
interface FarmDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(farm: Farm)

    @Delete
    suspend fun delete(farm: Farm)

    @Update
    suspend fun update(farm: Farm)

    @Query("SELECT * FROM farm")
    fun getAllFarms() : LiveData<List<Farm>>

    @Query("SELECT * FROM farm")
    fun getAllFarmsWithBlocks(): LiveData<List<FarmWithBlock>>

    @Query("SELECT farm.*, block.* FROM farm INNER JOIN block WHERE block.idBlock = :blockId AND farm.id = :farmId")
    fun getFarmAndBlock(blockId: Long, farmId: Long) : LiveData<FarmAndBlock>

}