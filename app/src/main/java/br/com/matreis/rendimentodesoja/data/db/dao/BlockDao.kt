package br.com.matreis.rendimentodesoja.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import br.com.matreis.rendimentodesoja.data.model.Block

@Dao
interface BlockDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertBlock(block: Block)

    @Delete
    suspend fun deleteBlock(block: Block)

    @Update
    suspend fun updateBlock(block: Block)

    @Query("SELECT * FROM block")
    fun getAllBlocks() : LiveData<List<Block>>

    @Query("SELECT * FROM block WHERE idBlock = :blockId")
    fun getBlockById(blockId: Long): LiveData<Block>

}