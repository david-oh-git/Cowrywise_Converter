package io.davidosemwota.core.data

/**
 * Represent a rate received from the Fixer IO.
 *
 * @param code The currency symbol.
 * @param rate The symbol rate value.
 */
data class Rate(
    val rate: Double,
    val code: String
)
