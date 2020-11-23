package io.davidosemwota.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.utils.DATABASE_VERSION

/**
 * Converter room database class
 */
@Database(
    entities = [Symbol::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class FixerIoDatabase : RoomDatabase() {

    /**
     * Get symbol dao [SymbolDao] object.
     */
    abstract fun symbolDao(): SymbolDao
}
