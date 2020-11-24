package io.davidosemwota.core.mapper

import com.google.common.truth.Truth.assertThat
import io.davidosemwota.core.network.responses.symbols.SymbolsListResponse
import io.davidosemwota.core.network.responses.symbols.SymbolsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class SymbolListMapperTest {

    private val symbolListMapper = SymbolListMapper()

    @Test
    @DisplayName("Mapper should transform SymbolListResponse to a list of symbols")
    fun mapperShouldTransformSymbol_confirm() = runBlockingTest {
        val symbolsMap = mutableMapOf<String, String>()
        val nairaKey = "NGN"
        val nairaValue = "Nigerian Naira"
        val poundsKey = "GBP"
        val poundsValue = "British pounds sterling"
        symbolsMap[nairaKey] = nairaValue
        symbolsMap[poundsKey] = poundsValue
        val symbolsResponse = SymbolsResponse(symbolsMap)
        val symbolsListResponse = SymbolsListResponse(
            success = true,
            error = null,
            symbols = symbolsResponse
        )

        symbolListMapper.transform(symbolsListResponse).run {
            assertThat(this.isEmpty()).isFalse()
            assertThat(this.size).isEqualTo(2)
        }
    }
}