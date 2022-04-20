package com.sample.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.data.entity.LatestExRateEntity

@Dao
interface LatestExRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateLatestRates(latestExRateEntity: LatestExRateEntity)

    @Query("SELECT * FROM latest_ex_rates ORDER BY timestamp DESC")
    suspend fun getLatestExRates(): List<LatestExRateEntity>?

    @Query("DELETE FROM latest_ex_rates")
    suspend fun deleteAllCatalogs()
}
