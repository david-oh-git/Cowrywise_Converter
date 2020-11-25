package io.davidosemwota.cowrywiseconverter.convertamount

import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.data.SymbolItem
import io.davidosemwota.core.mapper.Mapper

/**
 * Helper class to transform a list of [Symbol] Room entity input to a
 * list of [SymbolItem] output.
 */
class SymbolItemMapper : Mapper<List<Symbol>, List<SymbolItem>> {

    /**
     * Transforms input to desired output.
     *
     * @param from A list of symbol Room entity item [Symbol].
     * @return A list of [SymbolItem].
     */
    override suspend fun transform(from: List<Symbol>): List<SymbolItem> {
        return from.map { symbolToSymbolItem(it) }
    }

    /**
     *  Coverts [Symbol] item to [SymbolItem] item.
     */
    private fun symbolToSymbolItem(from: Symbol): SymbolItem {
        return SymbolItem(
            name = from.name,
            code = from.code
        )
    }
}
