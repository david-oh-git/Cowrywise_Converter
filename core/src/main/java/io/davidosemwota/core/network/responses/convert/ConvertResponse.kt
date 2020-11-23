package io.davidosemwota.core.network.responses.convert

import io.davidosemwota.core.network.responses.Error

/**
 * Generic network response from the fixer API convert endpoint.
 *
 * @param result The converted amount received from the API service.
 */
data class ConvertResponse(
    val success: Boolean,
    val query: Query?,
    val info: Info?,
    val date: String?,
    val result: Long?,
    val error: Error?
)
