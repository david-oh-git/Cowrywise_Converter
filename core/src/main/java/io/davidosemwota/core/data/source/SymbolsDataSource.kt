package io.davidosemwota.core.data.source

import io.davidosemwota.core.data.Rate
import io.davidosemwota.core.data.Symbol
import kotlinx.coroutines.flow.Flow

/**
 * Main entry point for accessing data.
 */
interface SymbolsDataSource {

    /**
     * Get a list of all currency [Symbol] from Fixer IO API.
     *
     * @param key The API request key.
     */
    suspend fun getSymbols(key: String): List<Symbol>

    /**
     * Get conversion rates from Fixer IO API.
     *
     * @param date The date.
     * @param key The API request key.
     * @param symbols The currency symbols to be converted. eg 'USD,CAD'
     */
    suspend fun getHistoricalRate(date: String, key: String, symbols: String): List<Rate>

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
     *
     * @param symbol Symbol item to be saved.
     */
    suspend fun save(symbol: Symbol)
}
