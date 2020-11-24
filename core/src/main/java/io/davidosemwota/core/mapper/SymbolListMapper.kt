package io.davidosemwota.core.mapper

import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.network.responses.symbols.SymbolsListResponse

/**
 * Helper class to transform [SymbolsListResponse] input to list of [Symbol] output.
 */
class SymbolListMapper : Mapper<SymbolsListResponse, List<Symbol>> {

    /**
     * Transforms input to desired output.
     *
     * @param from A Fixer IO API symbols endpoint response[SymbolsListResponse].
     * @return A characterDetail(parceble version) class
     */
    override suspend fun transform(from: SymbolsListResponse): List<Symbol> {
        return when (from.symbols) {
            null -> emptyList()
            else -> mapToList(from.symbols.data)
        }
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
