package io.davidosemwota.core.network.services

import io.davidosemwota.core.network.responses.rates.RatesListResponse
import io.davidosemwota.core.network.responses.symbols.SymbolsListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerIoService {

    /**
     * Get all currency symbols form Fixer IO API.
     *
     * @param key Request key for the API service.
     */
    @GET(SYMBOLS_ENDPOINT)
    suspend fun getAllSymbols(
        @Query("access_key") key: String
    ): SymbolsListResponse

    /**
     * Get conversion rates from Fixer IO API.
     *
     * @param date The date.
     * @param key The API request key.
     * @param symbols The from and to symbols to be converted. eg 'AED,NGN'
     */
    @GET("{date}")
    suspend fun getHistoricalRate(
        @Path("date") date: String,
        @Query("access_key") key: String,
        @Query("symbols", encoded = true) symbols: String
    ): RatesListResponse

    companion object {
        private const val SYMBOLS_ENDPOINT = "symbols"
    }
}
