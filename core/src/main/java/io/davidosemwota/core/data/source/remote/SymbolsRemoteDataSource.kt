package io.davidosemwota.core.data.source.remote

import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.data.source.SymbolsDataSource
import io.davidosemwota.core.mapper.Mapper
import io.davidosemwota.core.network.responses.symbols.SymbolsListResponse
import io.davidosemwota.core.network.services.FixerIoService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SymbolsRemoteDataSource(
    private val service: FixerIoService,
    private val mapper: Mapper<SymbolsListResponse, List<Symbol>>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SymbolsDataSource {

    /**
     * Gets a list of all available symbols from Fixer IO API.
     */
    override suspend fun getSymbols(key: String): List<Symbol> = withContext(ioDispatcher){
        val symbolsListResponse = service.getAllSymbols(key)
        return@withContext mapper.transform(symbolsListResponse)
    }

    /**
     * Not required here.
     */
    override fun getSymbolsFlow(): Flow<List<Symbol>> {
        TODO("Not yet implemented")
    }

    /**
     * Not required here.
     */
    override suspend fun getSymbols(): List<Symbol> {
        TODO("Not yet implemented")
    }

    /**
     * Not required here.
     */
    override suspend fun save(symbol: Symbol) {
        TODO("Not yet implemented")
    }
}
