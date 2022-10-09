package br.com.matreis.rendimentodesoja.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Farm::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idFarm"),
        onDelete = CASCADE
    )]
)
data class Block(
    @PrimaryKey(autoGenerate = true)
    val idBlock: Long,
    val idFarm: Long,
    var blockName: String,
    var size: Double
)