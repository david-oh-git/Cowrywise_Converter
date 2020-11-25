package io.davidosemwota.cowrywiseconverter.listofsymbols

import io.davidosemwota.core.base.BaseViewState

sealed class SymbolListViewState : BaseViewState {

    object Loading : SymbolListViewState()

    object Loaded : SymbolListViewState()

    fun isLoading() = this is Loading

    fun isLoaded() = this is Loaded

}
