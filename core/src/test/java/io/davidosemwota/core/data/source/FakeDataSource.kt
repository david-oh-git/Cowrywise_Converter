package io.davidosemwota.core.data.source

import io.davidosemwota.core.data.Symbol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Fake [SymbolsDataSource] for testing.
 */
class FakeDataSource(
    var symbols: MutableList<Symbol> = mutableListOf()
) : SymbolsDataSource {

    override suspend fun getSymbols(key: String): List<Symbol> {
        return symbols
    }

    override fun getSymbolsFlow(): Flow<List<Symbol>> {
        return MutableStateFlow(symbols)
    }

    override suspend fun getSymbols(): List<Symbol> {
        return symbols
    }

    override suspend fun save(symbol: Symbol) {
        symbols.add(symbol)
    }
}
