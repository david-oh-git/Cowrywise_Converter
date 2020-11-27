package io.davidosemwota.core.data.source

import android.content.SharedPreferences
import androidx.core.content.edit
import io.davidosemwota.core.data.Rate
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.utils.FROM_CODE_KEY
import io.davidosemwota.core.utils.TO_CODE_KEY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber

@ExperimentalCoroutinesApi
class DefaultRepository(
    private val localDataSource: SymbolsDataSource,
    private val remoteDataSource: SymbolsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val sharedPreferences: SharedPreferences
) : SymbolsRepository {

    override val fromCode = MutableStateFlow(
        sharedPreferences.getString(FROM_CODE_KEY, "NGN") ?: "NGN"
    )

    override val toCode: MutableStateFlow<String> = MutableStateFlow(
        sharedPreferences.getString(TO_CODE_KEY, "USD") ?: "USD"
    )

    override suspend fun getSymbols(key: String): List<Symbol> = withContext(ioDispatcher) {
        return@withContext remoteDataSource.getSymbols(key)
    }

    override suspend fun getHistoricalRate(date: String, key: String, symbols: String): List<Rate> =
        withContext(ioDispatcher) {
            return@withContext remoteDataSource.getHistoricalRate(
                date = date,
                key = key,
                symbols = symbols
            )
        }

    override fun getSymbolsFlow(): Flow<List<Symbol>> {
        return localDataSource.getSymbolsFlow()
    }

    override suspend fun getSymbols(): List<Symbol> = withContext(ioDispatcher) {
        return@withContext localDataSource.getSymbols()
    }

    override suspend fun save(symbol: Symbol) = withContext(ioDispatcher) {
        localDataSource.save(symbol)
    }

    override suspend fun setFromCode(code: String) {
    }

    override suspend fun setToCode(code: String) {
    }

    override suspend fun save(key: String, code: String) {
        Timber.d("About to save $key")
        when (key) {
            FROM_CODE_KEY -> {
                sharedPreferences.edit {
                    putString(FROM_CODE_KEY, code)
                }

                fromCode.value = code
            }
            TO_CODE_KEY -> {
                sharedPreferences.edit {
                    putString(TO_CODE_KEY, code)
                }

                toCode.value = code
            }
        }
    }
}
