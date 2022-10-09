package br.com.matreis.rendimentodesoja.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class FarmWithBlock(
    @Embedded
    val farm: Farm,
    @Relation(
        parentColumn = "id",
        entityColumn = "idFarm"
    )
    val blocks: List<Block>
)