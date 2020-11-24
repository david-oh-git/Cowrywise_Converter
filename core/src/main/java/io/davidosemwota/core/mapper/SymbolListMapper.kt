package io.davidosemwota.core.mapper

import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.network.responses.symbols.SymbolsListResponse
import io.davidosemwota.core.network.responses.symbols.SymbolsResponse

/**
 * Helper class to transform [SymbolsListResponse] input to list of [Symbol] output.
 */
open class SymbolListMapper : Mapper<SymbolsListResponse, List<Symbol>> {

    /**
     * Transforms input to desired output.
     *
     * @param from A Fixer IO API symbols endpoint response [SymbolsListResponse].
     * @return A list of symbols.
     */
    override suspend fun transform(from: SymbolsListResponse): List<Symbol> {
        val symbols: SymbolsResponse = from.symbols ?: return emptyList()

        return mapToList(symbols.data)
    }

    private fun mapToList(data: Map<String, String>): List<Symbol> {
        val listOfSymbols = mutableListOf<Symbol>()

        for ((key, value) in data) {
            val symbol = Symbol(0, key, value)
            listOfSymbols.add(symbol)
        }
        return listOfSymbols
    }
}
