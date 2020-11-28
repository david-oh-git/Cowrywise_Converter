package io.davidosemwota.core

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.room.Room
import io.davidosemwota.core.data.source.DefaultRepository
import io.davidosemwota.core.data.source.SymbolsDataSource
import io.davidosemwota.core.data.source.SymbolsRepository
import io.davidosemwota.core.data.source.local.FixerIoDatabase
import io.davidosemwota.core.data.source.local.SymbolsLocalDataSource
import io.davidosemwota.core.data.source.remote.SymbolsRemoteDataSource
import io.davidosemwota.core.mapper.RateMapper
import io.davidosemwota.core.mapper.SymbolListMapper
import io.davidosemwota.core.network.FixerIoApiFactory
import io.davidosemwota.core.utils.FIRST_TIME_INSTALL
import io.davidosemwota.core.utils.SYMBOL_FILE_NAME
import io.davidosemwota.core.workers.PopulateDatabaseRunnable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Single source of Data from core module.
 */
@ExperimentalCoroutinesApi
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
            RateMapper(),
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

    @ExperimentalStdlibApi
    fun firstTimePopulateDatabaseWithCurrencySymbols(context: Context) {
        val firstTimeLaunch = isFirstTimeLaunch(context)
        if (firstTimeLaunch)
            return

        Thread(PopulateDatabaseRunnable(context.applicationContext)).apply { start() }
    }

    private fun isFirstTimeLaunch(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
            .getBoolean(FIRST_TIME_INSTALL, false)
    }

    fun databaseInitialised(context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
            .edit {
                putBoolean(FIRST_TIME_INSTALL, true)
            }
    }
}
