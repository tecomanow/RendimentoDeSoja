package br.com.matreis.rendimentodesoja.data.model

import androidx.room.Embedded

data class FarmAndBlock(
    @Embedded
    val farm: Farm,
    @Embedded
    val block: Block
)