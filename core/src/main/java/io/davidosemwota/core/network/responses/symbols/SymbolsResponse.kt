package io.davidosemwota.core.network.responses.symbols

/**
 * Represent a part of the Fixer IO API Response.
 *
 * @param data A map of all currency code to currency name.
 */
data class SymbolsResponse(
    var data: Map<String, String> = mutableMapOf()
)
