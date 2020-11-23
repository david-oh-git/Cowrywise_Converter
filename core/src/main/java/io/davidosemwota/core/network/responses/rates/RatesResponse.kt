package io.davidosemwota.core.network.responses.rates

/**
 * Represent a part of the Fixer IO API Response.
 *
 * @param data A map of all currency code to currency rate value.
 */
data class RatesResponse(
    var data: Map<String, Long> = mutableMapOf()
)
