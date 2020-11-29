package io.davidosemwota.cowrywiseconverter.util.extentions

import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

/**
 * Send a snack bar message.
 */
fun sendSnackMessage(parent: ViewGroup, message: String) {
    Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show()
}
