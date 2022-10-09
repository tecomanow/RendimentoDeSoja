package br.com.matreis.rendimentodesoja.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Block::class,
        parentColumns = arrayOf("idBlock"),
        childColumns = arrayOf("idBlock"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Estimate(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val idBlock: Long,
    val date: String,
    val rowSpacing: Double,
    val numberSamplingPoint: Int,
    val sizeSamplingPoint: Double,
    val thousandGrainWeight: Double,
    val measurementSystem: Int,
    val description: String
)
