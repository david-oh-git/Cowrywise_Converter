package io.davidosemwota.cowrywiseconverter.convertamount

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class SymbolItemTest {

    @Test
    fun createSymbolItem_confirmCorrectAttributes() {
        val code = "GHS"
        val name = "Ghananian Cedi"
        val symbolItem = SymbolItem(code, name)

        assertThat(symbolItem.code).isEqualTo(code)
        assertThat(symbolItem.name).isEqualTo(name)
    }
}
