package io.davidosemwota.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.davidosemwota.core.utils.SYMBOL_TABLE_NAME

/**
 * Entity representing a currency symbol to be saved in the database.
 */
@Entity(tableName = SYMBOL_TABLE_NAME, indices = [Index(value = [ "name"], unique = true)])
data class Symbol(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val code: String,
    @ColumnInfo val name: String
)
