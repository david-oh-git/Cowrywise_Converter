package io.davidosemwota.cowrywiseconverter.convertamount

/**
 * Represents an item in the spinner.
 *
 * @param code The currency symbol code. Eg NGN
 * @param name The currency symbol name. Eg Ghanian Cedi.
 */
data class SymbolItem(
    val code: String,
    val name: String
)
