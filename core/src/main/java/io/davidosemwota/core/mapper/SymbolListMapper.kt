package io.davidosemwota.core.mapper

import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.network.responses.BaseResponse
import io.davidosemwota.core.network.responses.symbols.SymbolsListResponse

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
        val base: BaseResponse = from.symbols ?: return emptyList()

        return mapToList(base.data)
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
