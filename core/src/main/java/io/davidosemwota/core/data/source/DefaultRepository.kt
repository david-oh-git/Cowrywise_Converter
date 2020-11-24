package io.davidosemwota.core.data.source

import io.davidosemwota.core.data.Symbol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class DefaultRepository(
    private val localDataSource: SymbolsDataSource,
    private val remoteDataSource: SymbolsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SymbolsRepository {

    override suspend fun getSymbols(key: String): List<Symbol> = withContext(ioDispatcher) {
        return@withContext remoteDataSource.getSymbols(key)
    }

    override fun getSymbolsFlow(): Flow<List<Symbol>> {
        return localDataSource.getSymbolsFlow()
    }

    override suspend fun getSymbols(): List<Symbol>  = withContext(ioDispatcher) {
        return@withContext localDataSource.getSymbols()
    }

    override suspend fun save(symbol: Symbol)  = withContext(ioDispatcher) {
        localDataSource.save(symbol)
    }
}