package io.davidosemwota.cowrywiseconverter

import android.app.Application
import io.davidosemwota.core.ServiceLocator
import io.davidosemwota.core.data.source.SymbolsRepository
import timber.log.Timber

class ConverterApp : Application() {

    val repository: SymbolsRepository
        get() = ServiceLocator.provideRepository(this)

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    /**
     * Initialise [Timber] .
     */
    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}
