package com.jonathandarwin.data.database

import com.jonathandarwin.data.database.dao.CurrencyDAO
import com.jonathandarwin.domain.entity.CurrencyDTO

/**
 * Created By : Jonathan Darwin on June 02, 2021
 */
class FakeCurrencyDao : CurrencyDAO {
    val data = mutableListOf<CurrencyDTO>()

    override suspend fun insert(currencyDTO: CurrencyDTO): Long {
        data.add(currencyDTO)
        return data.size.toLong()
    }

    override suspend fun get(limit: Int): List<CurrencyDTO> {
        return data.subList(0, limit)
    }

    override suspend fun deleteAll(): Int {
        val size = data.size
        data.clear()
        return size
    }
}