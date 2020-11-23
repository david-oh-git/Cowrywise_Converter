package io.davidosemwota.core.network.responses.convert

/**
 * Represent a part of the Fixer IO API convert Response.
 *
 * @param timestamp The time response is received in unix time.
 * @param rate The exchange rate received from the API service.
 */
data class Info(
    val timestamp: Long,
    val rate: Long
)
