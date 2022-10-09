package br.com.matreis.rendimentodesoja.ui.fragment.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.matreis.rendimentodesoja.helper.PreferencesManager

class SettingsViewModel : ViewModel() {

    fun setMeasurementSystem(context: Context, measurementSystem: Int){
        val preferences = PreferencesManager(context)
        preferences.setMeasurementSystem(measurementSystem)
    }

    fun getMeasurementSystem(context: Context): Int {
        val preferences = PreferencesManager(context)
        return preferences.getMeasurementSystem()
    }

}