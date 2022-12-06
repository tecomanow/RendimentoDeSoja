package br.com.matreis.rendimentodesoja.helper

import br.com.matreis.rendimentodesoja.data.model.EstimateWithSamplingPoint
import java.text.NumberFormat
import java.util.*

class Calculations {

    companion object {
        fun getEstimateResult(estimateWithSamplingPoint: EstimateWithSamplingPoint): Double {

            var rowSpacing = estimateWithSamplingPoint.estimate.rowSpacing
            var thousandWeight = estimateWithSamplingPoint.estimate.thousandGrainWeight
            var samplingPointSize = estimateWithSamplingPoint.estimate.sizeSamplingPoint
            var averageNumberGrains = 0.0
            var numGrains = 0

            if (estimateWithSamplingPoint.estimate.measurementSystem == 1) {
                rowSpacing = rowSpacing.convertInchesToMeters()
                thousandWeight = thousandWeight.convertLbToKg()
                samplingPointSize = samplingPointSize.convertFeetToMeters()
            }

            estimateWithSamplingPoint.samplingPoints.forEach {
                numGrains += it.numberSeeds
            }

            averageNumberGrains = (numGrains / estimateWithSamplingPoint.samplingPoints.size).toDouble()

            return (averageNumberGrains * 10 * thousandWeight) / (samplingPointSize * rowSpacing)
        }
    }

}

