package io.davidosemwota.cowrywiseconverter.listofsymbols

import io.davidosemwota.core.base.BaseViewState

sealed class SymbolListViewState : BaseViewState {

    object Loading : SymbolListViewState()

    object LoadedFromSymbol : SymbolListViewState()

    object LoadedToSymbol : SymbolListViewState()

    fun isLoading() = this is Loading

    fun isLoadedFrom() = this is LoadedFromSymbol

    fun isLoadedTo() = this is LoadedToSymbol
}
