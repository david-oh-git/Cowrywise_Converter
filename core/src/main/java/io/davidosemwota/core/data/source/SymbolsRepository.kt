package io.davidosemwota.core.data.source

import io.davidosemwota.core.data.Symbol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *  Symbol repository.
 *
 *  Handles data operations
 */
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

    /**
     * Set from Symbol code
     */
    suspend fun setFromCode(code: String)

    /**
     * Set to Symbol code
     */
    suspend fun setToCode(code: String)

    /**
     * Save symbol code to data store.
     */
    suspend fun save(key: String, code: String)
}
