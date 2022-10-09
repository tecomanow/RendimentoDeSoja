package br.com.matreis.rendimentodesoja.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Farm(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name: String,
    var city: String
)