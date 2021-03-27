package com.jonathandarwin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jonathandarwin.domain.entity.CurrencyDTO
import java.util.*

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@Dao
interface CurrencyDAO {
    @Insert
    suspend fun insert(currencyDTO: CurrencyDTO): Long

    @Query("SELECT * FROM Currency ORDER BY datetime DESC LIMIT :limit")
    suspend fun get(limit: Int = 10): List<CurrencyDTO>

    @Query("DELETE FROM Currency")
    suspend fun deleteAll(): Int
}