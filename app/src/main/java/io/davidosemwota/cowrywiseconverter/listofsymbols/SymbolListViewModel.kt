package io.davidosemwota.cowrywiseconverter.listofsymbols

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.data.source.SymbolsRepository
import io.davidosemwota.core.mapper.Mapper
import io.davidosemwota.core.network.responses.symbols.SymbolsListResponse
import io.davidosemwota.cowrywiseconverter.convertamount.SymbolItem
import kotlinx.coroutines.flow.collect

class SymbolListViewModel(
    private val repository: SymbolsRepository,
    private val symbolListMapper: Mapper<SymbolsListResponse, List<Symbol>>
) : ViewModel() {

    val searchQuery = MutableLiveData<String>().apply { value = "" }
    private val symbols = liveData {
        repository.getSymbolsFlow()
            .collect {
                emit(it)
            }
    }

    val listOfSymbols: LiveData<List<SymbolItem>> =
        Transformations.switchMap(searchQuery) { query ->

            Transformations.switchMap(repository.getSymbolsFlow().asLiveData()) { symbols ->
                searchSymbols(query, symbols)
            }
        }

    val _state: MutableLiveData<SymbolListViewState> = MutableLiveData(SymbolListViewState.Loading)
    val state: LiveData<SymbolListViewState> = _state

    fun setState(state: SymbolListViewState) {
        _state.postValue(state)
    }

    private fun searchSymbols(query: String, symbols: List<Symbol>): LiveData<List<SymbolItem>> {

        if (query.isEmpty() || query.isBlank()) {
            return MutableLiveData(emptyList())
        }
        val searchSymbols = symbols.filter {
            it.name.contains(
                query, ignoreCase = true
            ) || it.code.contains(
                query, ignoreCase = true
            )
        }.map { SymbolItem(code = it.code, name = it.name) }

        return MutableLiveData(searchSymbols)
    }
}

@Suppress("UNCHECKED_CAST")
class SymbolListViewModelFactory(
    private val repository: SymbolsRepository,
    private val symbolListMapper: Mapper<SymbolsListResponse, List<Symbol>>
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (SymbolListViewModel(repository, symbolListMapper) as T)
}
