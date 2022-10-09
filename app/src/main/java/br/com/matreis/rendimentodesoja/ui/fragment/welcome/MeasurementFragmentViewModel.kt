package br.com.matreis.rendimentodesoja.ui.fragment.welcome

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.matreis.rendimentodesoja.helper.PreferencesManager

class MeasurementFragmentViewModel : ViewModel() {

    fun setMeasurementSystem(context: Context, measurementSystem: Int){
        val preferences = PreferencesManager(context)
        preferences.setMeasurementSystem(measurementSystem)
    }

    fun getMeasurementSystem(context: Context): Int {
        val preferences = PreferencesManager(context)
        return preferences.getMeasurementSystem()
    }

    fun setFirstRun(context: Context){
        val preferences = PreferencesManager(context)
        preferences.setIsFirstRun(false)
    }


}