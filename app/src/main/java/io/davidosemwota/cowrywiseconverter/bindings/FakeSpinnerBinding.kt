package io.davidosemwota.cowrywiseconverter.bindings

import androidx.databinding.BindingAdapter
import io.davidosemwota.cowrywiseconverter.customviews.FakeSpinner

/**
 * Sets text value for [FakeSpinner].
 *
 * @param text The text value.
 */
@BindingAdapter("fake_spinner_text")
fun FakeSpinner.setSpinnerText(text: String) {
    spinnerText = text
}
