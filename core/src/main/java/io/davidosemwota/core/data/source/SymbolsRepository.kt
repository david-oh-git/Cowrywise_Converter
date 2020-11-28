package io.davidosemwota.core.data.source

import io.davidosemwota.core.data.Rate
import io.davidosemwota.core.data.Symbol
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *  Symbol repository.
 *
 *  Handles data operations
 */
@ExperimentalCoroutinesApi
interface SymbolsRepository {

    /**
     * Currency symbol code converting from.
     */
    val fromCode: MutableStateFlow<String>

    /**
     * Currency symbol code converting to.
     */
    val toCode: MutableStateFlow<String>

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

    /**
     * Set convert from Symbol code.
     */
    suspend fun setFromCode(code: String)

    /**
     * Set convert to Symbol code.
     */
    suspend fun setToCode(code: String)

    /**
     * Save symbol code to shared preferences.
     */
    suspend fun save(key: String, code: String)
}
