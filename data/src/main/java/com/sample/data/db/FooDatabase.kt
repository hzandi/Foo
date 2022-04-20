package com.sample.data.db

import android.content.Context
import androidx.room.*
import com.sample.data.db.converter.ExchangeRateTypeConverter
import com.sample.data.db.dao.BalanceDao
import com.sample.data.db.dao.LatestExRatesDao
import com.sample.data.entity.BalanceEntity
import com.sample.data.entity.LatestExRateEntity

@Database(entities = [LatestExRateEntity::class, BalanceEntity::class], version = 1, exportSchema = false)
@TypeConverters(ExchangeRateTypeConverter::class)
abstract class FooDatabase : RoomDatabase() {

    abstract fun latestExRatesDao(): LatestExRatesDao
    abstract fun balanceDao(): BalanceDao

    companion object {
        @Volatile private var INSTANCE: FooDatabase? = null

        fun getInstance(context: Context): FooDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FooDatabase::class.java, "foo.db"
            ).allowMainThreadQueries().build()
    }
}
