package io.davidosemwota.core.network.responses.rates

import io.davidosemwota.core.network.responses.Error

/**
 * Generic network response from the fixer API rates endpoint.
 *
 * @param success Indicates if request was successful of not.
 * @param timestamp The time response is received in unix time.
 * @param base The base currency symbol.
 * @param date The date the response is received.
 * @param rates A map of rates to currency symbols.
 */
data class RatesListResponse(
    val success: Boolean,
    val timestamp: Long?,
    val base: String?,
    val date: String?,
    val rates: RatesResponse?,
    val error: Error?
)
