package io.davidosemwota.core.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

/**
 * Entity representing a currency symbol to be saved in the database.
 */
data class Symbol(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val code: String,
    @ColumnInfo val name: String
)
