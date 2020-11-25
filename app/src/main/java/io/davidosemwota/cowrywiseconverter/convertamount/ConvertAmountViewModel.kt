package io.davidosemwota.cowrywiseconverter.convertamount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import io.davidosemwota.core.data.source.SymbolsRepository
import kotlinx.coroutines.flow.collect

class ConvertAmountViewModel(
    private val repository: SymbolsRepository
) : ViewModel() {

    val fromCode = liveData {
        repository.fromCode.collect {
            emit(it)
        }
    }

    val toCode = liveData {
        repository.toCode.collect {
            emit(it)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ConvertAmountViewModelFactory(
    private val repository: SymbolsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (ConvertAmountViewModel(repository)) as T
}
