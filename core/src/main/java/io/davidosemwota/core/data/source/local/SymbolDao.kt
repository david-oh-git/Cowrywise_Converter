package io.davidosemwota.core.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.davidosemwota.core.data.Symbol
import io.davidosemwota.core.utils.SYMBOL_TABLE_NAME
import kotlinx.coroutines.flow.Flow

/**
 * Room Dao for [Symbol] entity.
 */
@Dao
interface SymbolDao {

    /**
     * Save a [Symbol] item to the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(symbol: Symbol)

    /**
     * Get all [Symbol] items from the database via a [Flow].
     */
    @Query("SELECT * FROM $SYMBOL_TABLE_NAME ORDER BY id")
    fun getSymbolsFlow(): Flow<List<Symbol>>

    /**
     * Get all [Symbol] items from the database.
     */
    @Query("SELECT * FROM $SYMBOL_TABLE_NAME ORDER BY id")
    fun getSymbols(): List<Symbol>
}
