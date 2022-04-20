package com.sample.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.data.entity.BalanceEntity

@Dao
interface BalanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateBalance(balanceEntity: BalanceEntity)

    @Query("SELECT * FROM balances ORDER BY value DESC")
    suspend fun getBalances(): List<BalanceEntity>?

    @Query("SELECT id FROM balances")
    suspend fun getBalanceIds(): List<Int>?

    @Query("SELECT * FROM balances WHERE symbol = :symbolParam")
    suspend fun getBalance(symbolParam: String): BalanceEntity?

    @Query("DELETE FROM balances WHERE id = :balanceId")
    suspend fun deleteBalance(balanceId: Int)

    @Query("DELETE FROM balances")
    suspend fun deleteAllBalances()
}
