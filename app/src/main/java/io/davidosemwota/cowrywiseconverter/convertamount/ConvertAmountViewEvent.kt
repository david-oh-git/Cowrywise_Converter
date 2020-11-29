package io.davidosemwota.cowrywiseconverter.convertamount

/**
 * Interaction events for [ConvertAmountFragment].
 */
sealed class ConvertAmountViewEvent {

    /**
     * Convert the currency amount entered.
     */
    object ConvertAmount : ConvertAmountViewEvent()

    /**
     * When no network is available.
     */
    object NoNetwork : ConvertAmountViewEvent()

    /**
     * Input text is empty.
     */
    object InputTextIsEmpty : ConvertAmountViewEvent()

    /**
     * When app fails to get data from API.
     */
    object ErrorFetchingRate : ConvertAmountViewEvent()
}
