package br.com.matreis.rendimentodesoja.data.model

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class EstimateWithSamplingPoint(

    @Embedded
    val estimate: Estimate,
    @Relation(
        parentColumn = "id",
        entityColumn = "idEstimate"
    )
    var samplingPoints: List<SamplingPoint>

) : Serializable
