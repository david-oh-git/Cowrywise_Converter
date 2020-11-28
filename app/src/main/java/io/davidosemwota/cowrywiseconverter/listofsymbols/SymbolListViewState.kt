package io.davidosemwota.cowrywiseconverter.listofsymbols

import io.davidosemwota.core.base.BaseViewState

/**
 * Represents viewState for SymbolListFragment
 */
sealed class SymbolListViewState : BaseViewState {

    /**
     * Loading state is displayed.
     */
    object Loading : SymbolListViewState()

    /**
     * List of symbols displayed.
     */
    object Loaded : SymbolListViewState()

    /**
     * Check if current view state is [Loading].
     */
    fun isLoading() = this is Loading

    /**
     * Check if current view state is [Loaded].
     */
    fun isLoaded() = this is Loaded
}
