package br.com.matreis.rendimentodesoja.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Estimate::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idEstimate"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class SamplingPoint(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var idEstimate: Long,
    var numberPlants: Int,
    var numberSeeds: Int,
    var numberPods: Int
)
