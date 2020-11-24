package io.davidosemwota.core.data.source

import com.google.common.truth.Truth.assertThat
import io.davidosemwota.core.data.Symbol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class DefaultRepositoryTest {

    private val naira = Symbol(73L, "NGN", "Nigerian Naira")
    private val dram = Symbol(22L, "AMD", "Armenian Dram")
    private val localSymbols = listOf(naira, dram)

    private val dirham = Symbol(22L, "AED", "United Arab Dirham")
    private val pounds = Symbol(42L, "GBP", "British Pounds ")
    private val remoteSymbols = listOf(dirham, pounds)

    private lateinit var localDataSource: SymbolsDataSource
    private lateinit var remoteDataSource: SymbolsDataSource

    private lateinit var repository: SymbolsRepository

    @BeforeEach
    fun init() {

        localDataSource = FakeDataSource(localSymbols.toMutableList())
        remoteDataSource = FakeDataSource(remoteSymbols.toMutableList())

        repository = DefaultRepository(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun getAllSymbolsEmptyLocalSource() = runBlockingTest {
        val emptySource = FakeDataSource()
        repository = DefaultRepository(emptySource, emptySource, Dispatchers.Unconfined)

        val result = repository.getSymbols()

        assertThat(result.isEmpty()).isTrue()
    }

    @Test
    fun getAllSymbolsFromLocalSource() = runBlockingTest {
        val result = repository.getSymbols()

        assertThat(result.isEmpty()).isFalse()
        assertThat(result).contains(naira)
        assertThat(result).contains(dram)
    }

    @Test
    fun getAllSymbolsFromRemoteSource() = runBlockingTest {
        val key = "FakeKey"
        val result = repository.getSymbols(key)

        assertThat(result.isEmpty()).isFalse()
        assertThat(result).contains(dirham)
        assertThat(result).contains(pounds)
    }

    @Test
    fun saveToLocalSource() = runBlockingTest {
        val cedi = Symbol(92L, "GHS", "Ghanaian Cedi")
        repository.save(cedi)

        val result = repository.getSymbols()

        assertThat(result.isEmpty()).isFalse()
        assertThat(result).contains(cedi)
        val resultSymbol = result.find { it.id == 92L }
        assertThat(resultSymbol).isEqualTo(cedi)
        assertThat(resultSymbol?.id).isEqualTo(cedi.id)
        assertThat(resultSymbol?.name).isEqualTo(cedi.name)
        assertThat(resultSymbol?.code).isEqualTo(cedi.code)
    }
}
