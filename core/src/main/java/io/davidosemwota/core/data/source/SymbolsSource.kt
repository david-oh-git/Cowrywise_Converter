package io.davidosemwota.core.data.source

import io.davidosemwota.core.data.Symbol
import kotlinx.coroutines.flow.Flow

interface SymbolsSource {

    fun getSymbols(key: String): List<Symbol>

    fun getSymbols(): Flow<List<Symbol>>

    fun save(symbol: Symbol)
}
