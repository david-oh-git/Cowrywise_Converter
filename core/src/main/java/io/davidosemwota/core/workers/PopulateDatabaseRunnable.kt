package io.davidosemwota.core.workers

import android.content.Context
import io.davidosemwota.core.BuildConfig
import io.davidosemwota.core.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import timber.log.Timber

class PopulateDatabaseRunnable(
    private val context: Context
) : Runnable {

    override fun run() {
        populateDatabaseWithSymbols(context)
    }

    private fun populateDatabaseWithSymbols(context: Context){
        val key = BuildConfig.FIXER_API_KEY

        GlobalScope.launch(Dispatchers.IO) {
            val repository = ServiceLocator.provideRepository(
                context.applicationContext
            )
            val symbols = repository.getSymbols(key)
            Timber.d("runable size is ${symbols.size}")

            symbols.forEach { repository.save(it) }
        }

    }
}
