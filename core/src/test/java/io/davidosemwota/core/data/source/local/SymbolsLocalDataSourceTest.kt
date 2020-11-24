package io.davidosemwota.core.data.source.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.utils.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
internal class SymbolsLocalDataSourceTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var localDataSource: SymbolsLocalDataSource
    private lateinit var database: FixerIoDatabase

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            FixerIoDatabase::class.java
        ).allowMainThreadQueries().build()

        localDataSource = SymbolsLocalDataSource(
            database.symbolDao(),
            Dispatchers.Main
        )
    }

    @After
    fun reset() {
        database.close()
    }

    @Test
    fun saveSymbolItem_confirmSaved() = runBlockingTest {
        val symbol = Symbol(23L, "NGN", "Nigerian Naira")
        localDataSource.save(symbol)

        val result = localDataSource.getSymbols()

        assertThat(result.size).isEqualTo(1)
        val item = result[0]
        assertThat(item.name).isEqualTo(symbol.name)
        assertThat(item.code).isEqualTo(symbol.code)
    }

    @Test
    fun saveMultipleSymbolItems_confirmedSaved() = runBlockingTest {
        val nairaSymbol = Symbol(23L, "NGN", "Nigerian Naira")
        val dollarSymbol = Symbol(42L, "USD", "American Dollar")
        localDataSource.save(nairaSymbol)
        localDataSource.save(dollarSymbol)

        val result = localDataSource.getSymbols()

        assertThat(result.isEmpty()).isFalse()
        assertThat(result.size).isEqualTo(2)
        assertThat(result).contains(nairaSymbol)
        assertThat(result).contains(dollarSymbol)
    }
}
