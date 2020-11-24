package io.davidosemwota.core.data.source.remote

import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.data.source.SymbolsDataSource
import io.davidosemwota.core.network.services.FixerIoService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class SymbolsRemoteDataSource(
    private val service: FixerIoService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SymbolsDataSource {

    override suspend fun getSymbols(key: String): List<Symbol> {
        TODO("Not yet implemented")
    }

    override fun getSymbolsFlow(): Flow<List<Symbol>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSymbols(): List<Symbol> {
        TODO("Not yet implemented")
    }

    override suspend fun save(symbol: Symbol) {
        TODO("Not yet implemented")
    }
}
