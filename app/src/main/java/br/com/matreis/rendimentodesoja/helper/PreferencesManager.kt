package br.com.matreis.rendimentodesoja.helper

import android.content.Context
import android.content.SharedPreferences

    class PreferencesManager(context: Context) {

    companion object {
        const val PREFERENCES_FILE = "soybeanCalculator_preferences"
        const val MEASUREMENT_SYSTEM = "measurement_system"
        const val IS_FIRST_RUN = "isFirstRun"
    }

    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

    fun setMeasurementSystem(measurementSystem: Int){
        with(preferences.edit()){
            putInt(MEASUREMENT_SYSTEM, measurementSystem)
            apply()
        }
    }

    fun getMeasurementSystem() : Int = preferences.getInt(MEASUREMENT_SYSTEM, -1)

    fun setIsFirstRun(isFirstRun: Boolean){
        with(preferences.edit()){
            putBoolean(IS_FIRST_RUN, isFirstRun)
            apply()
        }
    }

    fun getIsFirstRun() = preferences.getBoolean(IS_FIRST_RUN, true)

}