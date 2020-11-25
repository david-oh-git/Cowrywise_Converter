package io.davidosemwota.cowrywiseconverter.listofsymbols

/**
 * Interaction events for [SymbolListFragment].
 */
sealed class SymbolListViewEvent {

    /**
     * Save symbol code in the data store and close [SymbolListFragment].
     */
    data class SaveSymbolCodeAndClose(val code: String) : SymbolListViewEvent()
}
