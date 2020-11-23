package io.davidosemwota.cowrywiseconverter

import android.app.Application
import timber.log.Timber

class ConverterApp : Application() {

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
