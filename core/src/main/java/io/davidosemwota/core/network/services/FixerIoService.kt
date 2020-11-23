package io.davidosemwota.core.network.services

import io.davidosemwota.core.network.responses.convert.ConvertResponse
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
     * Get a list of rates from the API Service.
     *
     * @param key Request key for the API service.
     * @param base The base currency symbol.
     * @param symbols A list of comma separated currency symbols.
     */
    @GET(RATES_ENDPOINT)
    suspend fun getRates(
        @Query("access_key") key: String,
        @Query("base") base: String,
        @Query("symbols", encoded = true) symbols: String
    ): RatesListResponse

    /**
     * Converts from one currency symbol to another and returns the converted amount.
     */
    @GET(CONVERT_ENDPOINT)
    suspend fun convert(
        @Query("access_key") key: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Long
    ): ConvertResponse

    @GET("{date}")
    suspend fun getHistoricalRates(
        @Path("date") date: String,
        @Query("access_key") key: String,
        @Query("base") base: String,
        @Query("symbols", encoded = true) symbols: String
    ): RatesListResponse

    companion object {
        private const val SYMBOLS_ENDPOINT = "symbols"
        private const val RATES_ENDPOINT = "latest"
        private const val CONVERT_ENDPOINT = "convert"
    }
}
