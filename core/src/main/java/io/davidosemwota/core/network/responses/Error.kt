package io.davidosemwota.core.network.responses

/**
 * Generic network error response from the fixer API.
 *
 * @param code The HTTP status code
 * @param info Information about the error.
 */
data class Error(
    val code: Int,
    val info: String
)
