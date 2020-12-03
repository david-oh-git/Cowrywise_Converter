package io.davidosemwota.cowrywiseconverter.listofsymbols

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.data.SymbolItem
import io.davidosemwota.core.data.source.SymbolsRepository
import io.davidosemwota.core.mapper.Mapper
import io.davidosemwota.core.utils.SingleLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *
 */
@ExperimentalCoroutinesApi
class SymbolListViewModel(
    private val repository: SymbolsRepository,
    private val symbolListMapper: Mapper<List<Symbol>, List<SymbolItem>>
) : ViewModel() {

    val searchQuery = MutableLiveData<String>().apply { value = "" }
    val event = SingleLiveData<SymbolListViewEvent>()
    private val _state = MutableLiveData<SymbolListViewState>()
    private val symbols = liveData {
        repository.getSymbolsFlow()
            .collect {
                emit(it)
            }
    }

    val listOfSymbols: LiveData<List<SymbolItem>> = Transformations
        .switchMap(searchQuery) { query ->

            Transformations.switchMap(symbols) { symbolsList ->
                searchSymbols(query, symbolsList)
            }
        }

    val state = Transformations.map(symbols) {

        when {
            it.isNotEmpty() -> SymbolListViewState.Loaded
            it.isEmpty() -> {
                event.postValue(SymbolListViewEvent.NoSymbolsInDatabase)
                SymbolListViewState.Loading
            }
            else -> SymbolListViewState.Loading
        }
    }

    fun saveSymbolCodeAndCloseSymbolListFragment(code: String) {
        event.postValue(SymbolListViewEvent.SaveSymbolCodeAndClose(code))
    }

    fun save(key: String, code: String) = viewModelScope.launch {
        repository.save(key, code)
    }

    fun setState(state: SymbolListViewState) {
        _state.postValue(state)
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
            if (symbolItem.code.contains(query, ignoreCase = true) ||
                symbolItem.name.contains(query, ignoreCase = true)
            ) {
                newItems.add(symbolItem)
            }
        }

        return MutableLiveData(newItems)
    }
}

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class SymbolListViewModelFactory(
    private val repository: SymbolsRepository,
    private val symbolListMapper: Mapper<List<Symbol>, List<SymbolItem>>
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (SymbolListViewModel(repository, symbolListMapper) as T)
}
