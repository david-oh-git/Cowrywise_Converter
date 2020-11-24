package io.davidosemwota.core.data.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import io.davidosemwota.core.data.source.SymbolsDataSource
import io.davidosemwota.core.mapper.SymbolListMapper
import io.davidosemwota.core.network.services.FixerIoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
internal class SymbolsRemoteDataSourceTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var service: FixerIoService
    lateinit var mapper: SymbolListMapper
    private lateinit var remoteDataSource: SymbolsDataSource

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
        mapper = SymbolListMapper()

        remoteDataSource = SymbolsRemoteDataSource(
            service = service,
            mapper = mapper,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    @DisplayName(
        "When remoteDataSource getSymbols method is called , the service method " +
            "with the same name should be called with same args"
    )
    fun getSymbols() = runBlockingTest {
        val key = "fakeKey"
        val keyArg = argumentCaptor<String>()
        remoteDataSource.getSymbols(key)

        verify(service).getAllSymbols(keyArg.capture())

        assertThat(key).isEqualTo(keyArg.lastValue)
    }
}
