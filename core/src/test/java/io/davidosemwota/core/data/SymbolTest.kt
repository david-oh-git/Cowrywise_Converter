package io.davidosemwota.core.data

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class SymbolTest {

    @Test
    fun createSymbols_shouldAddCorrectAttributes() {
        val id = 23L
        val code = "GBP"
        val name = "Pounds Sterling"

        val symbol = Symbol(id, code, name)

        assertThat(symbol.id).isEqualTo(id)
        assertThat(symbol.code).isEqualTo(code)
        assertThat(symbol.name).isEqualTo(name)
    }
}
