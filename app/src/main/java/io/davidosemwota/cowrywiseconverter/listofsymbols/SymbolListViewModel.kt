package io.davidosemwota.cowrywiseconverter.listofsymbols

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.data.SymbolItem
import io.davidosemwota.core.data.source.SymbolsRepository
import io.davidosemwota.core.mapper.Mapper
import io.davidosemwota.core.utils.SingleLiveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SymbolListViewModel(
    private val repository: SymbolsRepository,
    private val symbolListMapper: Mapper<List<Symbol>, List<SymbolItem>>
) : ViewModel() {

    val searchQuery = MutableLiveData<String>().apply { value = "" }
    val symbols = liveData {
        repository.getSymbolsFlow()
            .collect {
                emit(symbolListMapper.transform(it))
            }
    }

    val listOfSymbols = Transformations.switchMap(searchQuery) { query ->

        Transformations.switchMap(repository.getSymbolsFlow().asLiveData()) { symbolsList ->
            searchSymbols(query, symbolsList)
        }
    }

    private val _state = MutableLiveData<SymbolListViewState>()
    val state = Transformations.map(symbols) {

        when {
            it.isNotEmpty() -> SymbolListViewState.Loaded
            it.isEmpty() -> SymbolListViewState.Loaded
            else -> SymbolListViewState.Loading
        }
    }

    val event = SingleLiveData<SymbolListViewEvent>()

    fun saveSymbolCodeAndCloseSymbolListFragment(code: String) {
        event.postValue(SymbolListViewEvent.SaveSymbolCodeAndClose(code))
        Timber.d("I was clicked !!")
    }

    fun save(key: String, code: String) = viewModelScope.launch {
        repository.save(key, code)
    }

    fun setState(state: SymbolListViewState) {
        _state.postValue(state)

        Timber.d("State set to ${_state.value}")
    }

    private fun searchSymbols(query: String?, symbols: List<Symbol>): LiveData<List<SymbolItem>> {
        var symbolItems: List<SymbolItem> = mutableListOf()
        viewModelScope.launch {
            symbolItems = symbolListMapper.transform(symbols)
        }
        if (query.isNullOrEmpty() || query.isBlank()) {
            return MutableLiveData(symbolItems)
        }

//        val searchSymbols = symbolItems.filter {
//            it.name.contains(
//                query, ignoreCase = true
//            ) || it.code.contains(
//                query, ignoreCase = true
//            )
//        }

        val newItems = mutableListOf<SymbolItem>()
        for (symbolItem in symbolItems) {
            if (query.contains(query, ignoreCase = true) ||
                query.contains(query, ignoreCase = true)
            ) {
                newItems.add(symbolItem)
            }
        }

        return MutableLiveData(newItems)
    }
}

@Suppress("UNCHECKED_CAST")
class SymbolListViewModelFactory(
    private val repository: SymbolsRepository,
    private val symbolListMapper: Mapper<List<Symbol>, List<SymbolItem>>
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (SymbolListViewModel(repository, symbolListMapper) as T)
}
