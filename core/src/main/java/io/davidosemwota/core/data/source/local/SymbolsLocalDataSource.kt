package io.davidosemwota.core.data.source.local

import io.davidosemwota.core.data.Rate
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.data.source.SymbolsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Entry point for [Symbol] table in the database.
 */
class SymbolsLocalDataSource(
    private val symbolDao: SymbolDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SymbolsDataSource {

    /**
     * Not required here.
     */
    override suspend fun getSymbols(key: String): List<Symbol> = withContext(ioDispatcher) {
        return@withContext emptyList<Symbol>()
    }

    /**
     * Get a list of all [Symbol] from the database via [Flow].
     */
    override fun getSymbolsFlow(): Flow<List<Symbol>> {
        return symbolDao.getSymbolsFlow()
    }

    /**
     * Get a list of all [Symbol] from the database.
     */
    override suspend fun getSymbols(): List<Symbol> = withContext(ioDispatcher) {
        return@withContext symbolDao.getSymbols()
    }

    /**
     * Save a [Symbol] item to the database.
     */
    override suspend fun save(symbol: Symbol) = withContext(ioDispatcher) {
        symbolDao.save(symbol)
    }

    /**
     * Not required here.
     */
    override suspend fun getHistoricalRate(date: String, key: String, symbols: String): List<Rate> {
        TODO("Not yet implemented")
    }
}
