package br.com.matreis.rendimentodesoja.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.matreis.rendimentodesoja.data.db.dao.BlockDao
import br.com.matreis.rendimentodesoja.data.db.dao.EstimateDao
import br.com.matreis.rendimentodesoja.data.db.dao.FarmDao
import br.com.matreis.rendimentodesoja.data.db.dao.SamplingPointDao
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.model.Estimate
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.data.model.SamplingPoint

@Database(
    entities = [Farm::class, Block::class, Estimate::class, SamplingPoint::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFarmDao() : FarmDao
    abstract fun getBlockDao() : BlockDao
    abstract fun getEstimateDao() : EstimateDao
    abstract fun getSamplingPointDao() : SamplingPointDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                   context.applicationContext,
                   AppDatabase::class.java,
                   "soybeanCalculator_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}