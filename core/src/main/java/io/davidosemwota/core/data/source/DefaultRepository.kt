package io.davidosemwota.core.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.utils.FROM_CODE_KEY
import io.davidosemwota.core.utils.TO_CODE_KEY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultRepository(
    private val localDataSource: SymbolsDataSource,
    private val remoteDataSource: SymbolsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dataStore: DataStore<Preferences>
) : SymbolsRepository {

    private val FROM_PREFERENCE_KEY = preferencesKey<String>(FROM_CODE_KEY)
    private val TO_PREFERENCE_KEY = preferencesKey<String>(TO_CODE_KEY)

    val fromCode: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[ FROM_PREFERENCE_KEY ] ?: "NGN"
        }

    val toCode: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[TO_PREFERENCE_KEY] ?: "USD"
        }

    override suspend fun getSymbols(key: String): List<Symbol> = withContext(ioDispatcher) {
        return@withContext remoteDataSource.getSymbols(key)
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
        dataStore.edit { preferences ->
            preferences[FROM_PREFERENCE_KEY] = code
        }
    }

    override suspend fun setToCode(code: String) {
        dataStore.edit { preferences ->
            preferences[TO_PREFERENCE_KEY] = code
        }
    }
}
