package io.davidosemwota.core.network.responses.symbols

import io.davidosemwota.core.network.responses.Error

/**
 * Generic network response from the fixer API.
 *
 * @param success Indicates if request was successful of not.
 * @param symbols A map of all currency codes and their names.
 * @param error Fixer API error response.
 */
data class SymbolsListResponse(
    val success: Boolean,
    val symbols: SymbolsResponse?,
    val error: Error?
)
