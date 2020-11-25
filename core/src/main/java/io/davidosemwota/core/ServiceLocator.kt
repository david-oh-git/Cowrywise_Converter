package io.davidosemwota.core

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.preference.PreferenceManager
import androidx.room.Room
import io.davidosemwota.core.data.source.DefaultRepository
import io.davidosemwota.core.data.source.SymbolsDataSource
import io.davidosemwota.core.data.source.SymbolsRepository
import io.davidosemwota.core.data.source.local.FixerIoDatabase
import io.davidosemwota.core.data.source.local.SymbolsLocalDataSource
import io.davidosemwota.core.data.source.remote.SymbolsRemoteDataSource
import io.davidosemwota.core.mapper.SymbolListMapper
import io.davidosemwota.core.network.FixerIoApiFactory
import io.davidosemwota.core.utils.PREFERENCES_FILE_NAME
import io.davidosemwota.core.utils.SYMBOL_FILE_NAME
import io.davidosemwota.core.workers.PopulateDatabaseRunnable
import kotlinx.coroutines.Dispatchers

object ServiceLocator {

    private var database: FixerIoDatabase? = null
    var repository: SymbolsRepository? = null

    fun provideRepository(context: Context): SymbolsRepository {
        synchronized(this) {
            return repository ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): SymbolsRepository {

        PreferenceManager.getDefaultSharedPreferences(context)
        val newRepo = DefaultRepository(
            localDataSource = createLocalDataSource(context),
            remoteDataSource = createRemoteDataSource(),
            Dispatchers.IO,
            PreferenceManager.getDefaultSharedPreferences(context)
        )

        repository = newRepo

        return newRepo
    }

    private fun createLocalDataSource(context: Context): SymbolsDataSource {
        val database = database ?: createDatabase(context)
        return SymbolsLocalDataSource(database.symbolDao(), Dispatchers.IO)
    }

    private fun createRemoteDataSource(): SymbolsDataSource {
        return SymbolsRemoteDataSource(
            FixerIoApiFactory.provideFixerApiService(),
            SymbolListMapper(),
            Dispatchers.IO
        )
    }

    private fun createDatabase(context: Context): FixerIoDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            FixerIoDatabase::class.java,
            SYMBOL_FILE_NAME
        ).build()

        database = result

        return result
    }

    fun firstTimePopulateDatabaseWithCurrencySymbols(context: Context){
        Thread( PopulateDatabaseRunnable(context.applicationContext)).apply { start() }
    }
}
