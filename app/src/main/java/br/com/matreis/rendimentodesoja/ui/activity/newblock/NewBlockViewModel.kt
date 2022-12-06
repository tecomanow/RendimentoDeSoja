package br.com.matreis.rendimentodesoja.ui.activity.newblock

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepository
import br.com.matreis.rendimentodesoja.helper.PreferencesManager
import br.com.matreis.rendimentodesoja.ui.activity.newfarm.FarmViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewBlockViewModel(private val repository: SoybeanCalculatorRepository): ViewModel() {

    fun insertBlock(block: Block){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertBlock(block)
        }
    }

    fun getAllFarm() = repository.getAllFarms()

    fun getMeasurementSystem(context: Context): Int{
        return PreferencesManager(context).getMeasurementSystem()
    }

    class NewBlockViewModelFactory(private val repository: SoybeanCalculatorRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if(modelClass.isAssignableFrom(NewBlockViewModel::class.java)){
                NewBlockViewModel(repository) as T
            }else{
                super.create(modelClass)
            }
        }
    }
}