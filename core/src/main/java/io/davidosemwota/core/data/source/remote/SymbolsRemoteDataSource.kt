package io.davidosemwota.core.data.source.remote

import io.davidosemwota.core.data.Rate
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.data.source.SymbolsDataSource
import io.davidosemwota.core.mapper.Mapper
import io.davidosemwota.core.network.responses.rates.RatesListResponse
import io.davidosemwota.core.network.responses.symbols.SymbolsListResponse
import io.davidosemwota.core.network.services.FixerIoService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SymbolsRemoteDataSource(
    private val service: FixerIoService,
    private val symbolMapper: Mapper<SymbolsListResponse, List<Symbol>>,
    private val rateMapper: Mapper<RatesListResponse, List<Rate>>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SymbolsDataSource, BaseRemoteDataSource() {

    /**
     * Gets a list of all available symbols from Fixer IO API.
     */
    override suspend fun getSymbols(key: String): List<Symbol> = withContext(ioDispatcher) {

        val symbolsListResponse = safeApiCall(
            call = { service.getAllSymbols(key) },
            errorMsg = "Error fetching ..."
        )
        return@withContext symbolsListResponse?.let {
            symbolMapper.transform(symbolsListResponse)
        } ?: emptyList()
    }

    /**
     * Get conversion rates from Fixer IO API.
     *
     * @param date The date.
     * @param key The API request key.
     * @param symbols The currency symbols to be converted. eg 'USD,CAD'
     */
    override suspend fun getHistoricalRate(date: String, key: String, symbols: String): List<Rate> =
        withContext(ioDispatcher) {

            val rateResponse = safeApiCall(
                call = {
                    service.getHistoricalRate(
                        date = date,
                        key = key,
                        symbols = symbols
                    )
                },
                errorMsg = "Error fetching ..."
            )

            return@withContext rateResponse?.let { rateMapper.transform(it) } ?: emptyList()
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
