package com.jonathandarwin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jonathandarwin.data.database.dao.CurrencyDAO
import com.jonathandarwin.domain.entity.CurrencyDTO

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
@Database(
        entities = [CurrencyDTO::class],
        version = 1,
        exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val currencyDAO: CurrencyDAO

    companion object {
        var instance : AppDatabase? = null
        val slock = Object()

        fun getInstance(context: Context): AppDatabase {
            synchronized(slock) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "Currency.db").build()
                }
                return instance as AppDatabase
            }
        }
    }
}