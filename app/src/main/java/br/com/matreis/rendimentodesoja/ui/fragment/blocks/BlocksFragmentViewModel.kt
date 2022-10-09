package br.com.matreis.rendimentodesoja.ui.fragment.blocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.matreis.rendimentodesoja.data.model.Block
import br.com.matreis.rendimentodesoja.data.repository.SoybeanCalculatorRepository
import br.com.matreis.rendimentodesoja.ui.fragment.farms.FarmsFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BlocksFragmentViewModel(private val repository: SoybeanCalculatorRepository): ViewModel() {

    fun getAllFarmsAndBlocks() = repository.getAllFarmsWithBlocks()

    fun deleteBlock(block: Block) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBlock(block)
        }
    }

    fun updateBlock(block: Block){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBlock(block)
        }
    }

    class BlocksFragmentViewModelFactory(private val repository: SoybeanCalculatorRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if(modelClass.isAssignableFrom(BlocksFragmentViewModel::class.java)){
                BlocksFragmentViewModel(repository) as T
            }else{
                super.create(modelClass)
            }
        }
    }
}