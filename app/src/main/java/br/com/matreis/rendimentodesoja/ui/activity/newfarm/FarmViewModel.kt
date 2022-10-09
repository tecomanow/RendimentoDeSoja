package br.com.matreis.rendimentodesoja.ui.activity.newfarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FarmViewModel(private val repository: SoybeanCalculatorRepository) : ViewModel() {

    fun insertFarm(name: String, city: String){
        viewModelScope.launch(Dispatchers.IO) {
            val farm = Farm(
                id = 0,
                name = name,
                city = city
            )
            repository.insertFarm(farm)
        }
    }

    fun UpdateFarm(farm: Farm){
        viewModelScope.launch(Dispatchers.IO) {
            //repository.updateFarm(farm)
        }
    }

    class MyFarmViewModelFactory(private val repository: SoybeanCalculatorRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if(modelClass.isAssignableFrom(FarmViewModel::class.java)){
                FarmViewModel(repository) as T
            }else{
                super.create(modelClass)
            }
        }
    }
}