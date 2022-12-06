package br.com.matreis.rendimentodesoja.ui.activity.results

import br.com.matreis.rendimentodesoja.data.model.Estimate
import br.com.matreis.rendimentodesoja.data.model.EstimateWithSamplingPoint
import br.com.matreis.rendimentodesoja.data.model.SamplingPoint
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepository
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

internal class EstimateResultsViewModelTest {

    private lateinit var estimateResultsViewModel: EstimateResultsViewModel
    private lateinit var repository: SoybeanCalculatorRepository

    @Before
    fun setUp(){
        repository = Mockito.mock(SoybeanCalculatorRepository::class.java)
        estimateResultsViewModel = EstimateResultsViewModel(repository)
        //Mockito.`when`(estimateResultsViewModel.getEstimateResult()).thenReturn(5214.0)
    }

    @Test
    fun getEstimateResult_estimateWithSamplingPointSent_returnEstimateResult() {
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
        estimateResultsViewModel._estimateWithSamplingPoint.value = estimateWithSamplingPoint
        val result = estimateResultsViewModel.getEstimateResult()
        Truth.assertThat(result).isEqualTo(5214.0)

    }
}