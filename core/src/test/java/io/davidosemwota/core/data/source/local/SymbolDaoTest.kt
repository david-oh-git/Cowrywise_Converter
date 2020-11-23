package io.davidosemwota.core.data.source.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
internal class SymbolDaoTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var database: FixerIoDatabase
    private lateinit var symbolDao: SymbolDao

    @Before
    fun init() {
        val applicationContext = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            applicationContext.applicationContext,
            FixerIoDatabase::class.java
        ).allowMainThreadQueries().build()

        symbolDao = database.symbolDao()
    }

    @After
    fun reset() {
        database.close()
    }

    @Test
    fun saveSymbol_getSymbol() = runBlockingTest {
        val symbol = Symbol(23L, "NGN", "Nigerian Naira")
        symbolDao.save(symbol)

        val results = symbolDao.getSymbolsFlow().take(1).toList()

        assertThat(results.size).isEqualTo(1)
    }

    @Test
    fun saveMultipleSymbolItems_confirmAllSaved() = runBlockingTest {
        val nairaSymbol = Symbol(23L, "NGN", "Nigerian Naira")
        val dollarSymbol = Symbol(42L, "USD", "American Dollar")
        symbolDao.save(nairaSymbol)
        symbolDao.save(dollarSymbol)

        val results = symbolDao.getSymbols()

        assertThat(results.isEmpty()).isFalse()
        assertThat(results.size).isEqualTo(2)
    }
}
