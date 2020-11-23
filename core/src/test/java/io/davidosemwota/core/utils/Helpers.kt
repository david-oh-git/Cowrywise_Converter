package io.davidosemwota.core.utils

import com.google.common.io.Resources
import java.io.File
import java.net.URL

/**
 * Read data from .JSON file and covert output to string.
 */
@Suppress("UnstableApiUsage")
internal fun getJson(path: String): String {
    val uri: URL = Resources.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}
