package br.com.matreis.rendimentodesoja

import br.com.matreis.rendimentodesoja.data.model.Estimate
import br.com.matreis.rendimentodesoja.data.model.EstimateWithSamplingPoint
import br.com.matreis.rendimentodesoja.data.model.SamplingPoint
import br.com.matreis.rendimentodesoja.helper.Calculations
import br.com.matreis.rendimentodesoja.helper.convertFeetToMeters
import br.com.matreis.rendimentodesoja.helper.convertInchesToMeters
import br.com.matreis.rendimentodesoja.helper.convertLbToKg
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import com.google.common.truth.Truth.assertWithMessage
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class EstimateTest {
    
    @Test
    fun getEstimate(){

        val estimate = Estimate(
            0,
            0,
            "",
            0.5,
            1,
            1.0,
            0.15,
            0,
            ""
        )
        val samplingPoint = SamplingPoint(
            0,
            0,
            0,
            1738,
            0
        )

        val samplingPoints = ArrayList<SamplingPoint>();
        samplingPoints.add(samplingPoint)

        val estimateWithSamplingPoint = EstimateWithSamplingPoint(estimate, samplingPoints)
        val result = Calculations.getEstimateResult(estimateWithSamplingPoint)
        assertThat(result).isEqualTo(5214.0)
    }


}