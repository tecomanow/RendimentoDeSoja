package br.com.matreis.rendimentodesoja.ui.fragment.farms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.matreis.rendimentodesoja.data.model.Farm
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FarmsFragmentViewModel(private val repository: SoybeanCalculatorRepository): ViewModel() {

    fun getAllFarms() = repository.getAllFarms()

    fun deleteFarm(farm: Farm){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFarm(farm)
        }
    }

    fun updateFarm(farm: Farm) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFarm(farm)
        }
    }

    class FarmsFragmentViewModelFactory(private val repository: SoybeanCalculatorRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if(modelClass.isAssignableFrom(FarmsFragmentViewModel::class.java)){
                FarmsFragmentViewModel(repository) as T
            }else{
                super.create(modelClass)
            }
        }
    }
}