package io.davidosemwota.core.data.source

import io.davidosemwota.core.data.Symbol
import kotlinx.coroutines.flow.Flow

/**
 * Main entry point for accessing data.
 */
interface SymbolsDataSource {

    /**
     * Get a list of all currency [Symbol] from Fixer IO API.
     */
    suspend fun getSymbols(key: String): List<Symbol>

    /**
     * Get a list of all [Symbol] from the database via [Flow].
     */
    fun getSymbolsFlow(): Flow<List<Symbol>>

    /**
     * Get a list of all [Symbol] from the database.
     */
    suspend fun getSymbols(): List<Symbol>

    /**
     * Save a [Symbol] item to the database.
     */
    suspend fun save(symbol: Symbol)
}
