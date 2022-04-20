package com.sample.data.db.repository

import com.sample.data.entity.BalanceEntity

interface LocalBalanceRepository {

    suspend fun insertOrUpdateBalance(balanceEntity: BalanceEntity)

    suspend fun getBalances(): List<BalanceEntity>?

    suspend fun getBalanceIds(): List<Int>?

    suspend fun getBalance(symbolParam: String): BalanceEntity?

    suspend fun deleteBalance(balanceId: Int)

    suspend fun deleteAllBalances()
}
