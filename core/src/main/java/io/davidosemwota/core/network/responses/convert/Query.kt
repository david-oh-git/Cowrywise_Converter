package io.davidosemwota.core.network.responses.convert

/**
 * Represent a part of the Fixer IO API convert Response.
 *
 * @param from Currency symbol to convert from. eg USD.
 * @param to Currency symbol to convert to. eg. NGN.
 * @param amount The amount to be converted.
 */
data class Query(
    val from: String,
    val to: String,
    val amount: Double
)
