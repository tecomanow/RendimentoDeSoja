package br.com.matreis.rendimentodesoja.ui.activity.results

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.matreis.rendimentodesoja.App
import br.com.matreis.rendimentodesoja.R
import br.com.matreis.rendimentodesoja.data.model.EstimateWithSamplingPoint
import br.com.matreis.rendimentodesoja.data.model.FarmAndBlock
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepositoryImp
import br.com.matreis.rendimentodesoja.data.repository.datasourceImp.LocalDatasourceImp
import br.com.matreis.rendimentodesoja.databinding.ActivitySoybeanYieldsResultsBinding
import br.com.matreis.rendimentodesoja.helper.*
import br.com.matreis.rendimentodesoja.ui.activity.MainActivity
import br.com.matreis.rendimentodesoja.ui.adapter.SamplingPointsViewAdapter
import java.text.NumberFormat
import java.util.*

class SoybeanYieldsResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoybeanYieldsResultsBinding
    private lateinit var resultsViewModel: EstimateResultsViewModel
    private val adapterSamplingPointView by lazy { SamplingPointsViewAdapter() }

    private var estimateId = 0L
    private var actualMeasurementSystem: Int? = null
    private var estimateWithSamplingPoint: EstimateWithSamplingPoint? = null
    private var isMetricSystem: Boolean = true

    private var numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale("pt","BR"))
    private var numberFormatEua: NumberFormat = NumberFormat.getNumberInstance(Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoybeanYieldsResultsBinding.inflate(layoutInflater)
        setUpListener()
        setContentView(binding.root)
        resultsViewModel = ViewModelProvider(
            this, EstimateResultsViewModel.EstimateResultsViewModelFactory(
                SoybeanCalculatorRepositoryImp(
                    LocalDatasourceImp(
                        (application as App).database.getFarmDao(),
                        (application as App).database.getBlockDao(),
                        (application as App).database.getEstimateDao()
                    )
                )
            )
        )[EstimateResultsViewModel::class.java]


        estimateId = intent.getLongExtra("estimateId", 0L)
        resultsViewModel.getEstimateWithSamplingPoints(estimateId)

        setUpCustomToolbar()
        setUpRecyclerView()
        setUpObservers()
    }

    private fun setUpObservers() {
        resultsViewModel.estimateWithSamplingPoint.observe(this) { estimate ->
            estimateWithSamplingPoint = estimate
            resultsViewModel.getBlockById(estimate.estimate.idBlock).observe(this){
                resultsViewModel.getFarmAndBlock(it.idBlock, it.idFarm).observe(this) { farmAndBlock ->
                    setUpView(estimate, farmAndBlock)
                }
            }
            adapterSamplingPointView.setSamplingPointList(estimate.samplingPoints)
        }
    }

    private fun setUpView(
        estimateWithSamplingPoint: EstimateWithSamplingPoint,
        farmAndBlock: FarmAndBlock
    ) {
        estimateWithSamplingPoint.estimate.let { estimate ->
            binding.apply {

                actualMeasurementSystem = estimate.measurementSystem

                val rowSpacing = estimate.rowSpacing
                val weigh1000Grains = estimate.thousandGrainWeight
                val samplingPointSize = estimate.sizeSamplingPoint
                //var estimateResults = getEstimateResult(estimateWithSamplingPoint)
                var estimateResults = resultsViewModel.getEstimateResult()


                if (estimate.measurementSystem == 0) {
                    btnConvertMeasurementSystem.text = "Exibir no sistema imperial"

                    val samplingPointSizeString =  "$samplingPointSize ${resources.getString(R.string.meters)}"
                    val rowSpacingString =  "$rowSpacing ${resources.getString(R.string.meters)}"
                    val weigh1000GrainsString =  "$weigh1000Grains ${resources.getString(R.string.kg)}"
                    //val estimateResultsString = "${String.format(Locale.getDefault(),"%.2f",estimateResults)} ${resources.getString(R.string.kg_hectares)}"
                    val estimateResultsString = "${numberFormat.format(estimateResults)} ${resources.getString(R.string.kg_hectares)}"

                    textViewSimpledPointsSizeValue.text = samplingPointSizeString
                    textViewRowSpacingValue.text = rowSpacingString
                    textViewWeigh1000GrainsValue.text = weigh1000GrainsString
                    textViewEstimateResultValue.text = estimateResultsString
                    textViewtextViewMeasurementSystemValue.text = "Métrico"

                } else if (estimate.measurementSystem == 1) {
                    btnConvertMeasurementSystem.text = "Exibir no sistema métrico"
                    estimateResults = estimateResults.convertEstimateToImperialSystem()

                    val samplingPointSizeString =  "$samplingPointSize ${resources.getString(R.string.feet)}"
                    val rowSpacingString =  "$rowSpacing ${resources.getString(R.string.inches)}"
                    val weigh1000GrainsString =  "$weigh1000Grains ${resources.getString(R.string.lb)}"
                    //val estimateResultsString = "${String.format(Locale.getDefault(),"%.2f",estimateResults)} ${resources.getString(R.string.bushels_acre)}"
                    val estimateResultsString = "${numberFormatEua.format(estimateResults)} ${resources.getString(R.string.bushels_acre)}"

                    textViewSimpledPointsSizeValue.text = samplingPointSizeString
                    textViewRowSpacingValue.text = rowSpacingString
                    textViewWeigh1000GrainsValue.text = weigh1000GrainsString
                    textViewEstimateResultValue.text = estimateResultsString
                    textViewtextViewMeasurementSystemValue.text = "Imperial"
                }


                textViewDateValue.text = estimate.date
                textViewSimpledPointsValue.text = estimate.numberSamplingPoint.toString()
                textViewFarmValue.text = farmAndBlock.farm.name
                textViewBlockValue.text = farmAndBlock.block.blockName

            }
        }
    }

    private fun showResultsInDifferentSystem() {
        estimateWithSamplingPoint?.estimate?.let { estimate ->
            binding.apply {

                //actualMeasurementSystem = estimate.measurementSystem

                val rowSpacing = estimate.rowSpacing
                val weigh1000Grains = estimate.thousandGrainWeight
                val samplingPointSize = estimate.sizeSamplingPoint
                var estimateResults = resultsViewModel.getEstimateResult()

                val sampledPointString: String
                val rowSpacingString: String
                val weigh1000GrainsString: String
                val results: String

                if(isMetricSystem){
                    //btnConvertMeasurementSystem.text = "Exibir no sistema imperial"

                    //sampledPointString = "${String.format(Locale.getDefault(), "%.2f", samplingPointSize.convertFeetToMeters())} ${resources.getString(R.string.meters)}"
                    sampledPointString = "${numberFormat.format(samplingPointSize.convertFeetToMeters())} ${resources.getString(R.string.meters)}"
                    rowSpacingString = "${numberFormat.format(rowSpacing.convertInchesToMeters())} ${resources.getString(R.string.meters)}"
                    weigh1000GrainsString = "${numberFormat.format(weigh1000Grains.convertLbToKg())} ${resources.getString(R.string.kg)}"
                    results = "${numberFormat.format(estimateResults)} ${resources.getString(R.string.kg_hectares)}"


                }else {
                    isMetricSystem = false
                    //btnConvertMeasurementSystem.text = "Exibir no sistema métrico"
                    estimateResults = estimateResults.convertEstimateToImperialSystem()

                    sampledPointString = "${numberFormatEua.format(samplingPointSize.convertMetersToFeet())} ${resources.getString(R.string.feet)}"
                    rowSpacingString = "${numberFormatEua.format(rowSpacing.convertMetersToInches())} ${resources.getString(R.string.inches)}"
                    weigh1000GrainsString =  "${numberFormatEua.format(weigh1000Grains.convertKgToLb())} ${resources.getString(R.string.lb)}"
                    results = "${numberFormatEua.format(estimateResults)} ${resources.getString(R.string.bushels_acre)}"

                }

                textViewSimpledPointsSizeValue.text = sampledPointString
                textViewRowSpacingValue.text = rowSpacingString
                textViewWeigh1000GrainsValue.text = weigh1000GrainsString
                //textViewEstimateResultValue.text = "${String.format(Locale.getDefault(), "%.2f", estimateResults)} ${resources.getString(R.string.kg_hectares)}"
                textViewEstimateResultValue.text = results

            }
        }
    }

    private fun setUpListener() {
        binding.btnBackToHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
        binding.toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if(isChecked){

                if(actualMeasurementSystem == 0){
                    isMetricSystem = false
                    showResultsInDifferentSystem()

                }else{
                    isMetricSystem = true
                    showResultsInDifferentSystem()
                }

            }else{
                //setUpView(estimateWithSamplingPoint!!, farmAndBlock!!)
                resultsViewModel.getEstimateWithSamplingPoints(estimateId)
            }
        }
    }

    private fun setUpCustomToolbar() {
        binding.toolbar.textToolbar.text = "Resultado da estimativa"
        binding.toolbar.imgBtnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }

    private fun setUpRecyclerView() {
        binding.rvSamplingPointsView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                this@SoybeanYieldsResultsActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = adapterSamplingPointView
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
        super.onBackPressed()
    }
}