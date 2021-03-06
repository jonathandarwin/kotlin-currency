package com.jonathandarwin.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@Entity(tableName = "Currency")
data class CurrencyDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "from")
    val from: String,

    @ColumnInfo(name = "to")
    val to: String,

    @ColumnInfo(name = "amount")
    val amount: String,

    @ColumnInfo(name = "result")
    val result: String,

    @ColumnInfo(name = "rate")
    val rate: String,

    @ColumnInfo(name = "datetime")
    val datetime: Long
)