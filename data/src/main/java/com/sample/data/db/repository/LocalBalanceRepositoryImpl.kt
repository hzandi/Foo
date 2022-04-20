package com.sample.data.db.repository

import com.sample.data.db.dao.BalanceDao
import com.sample.data.entity.BalanceEntity
import javax.inject.Inject

class LocalBalanceRepositoryImpl @Inject constructor(
    private val balanceDao: BalanceDao
): LocalBalanceRepository {
    override suspend fun insertOrUpdateBalance(balanceEntity: BalanceEntity) {
        balanceDao.insertOrUpdateBalance(balanceEntity)
    }

    override suspend fun getBalances(): List<BalanceEntity>? {
        return balanceDao.getBalances()
    }

    override suspend fun getBalanceIds(): List<Int>? {
        return balanceDao.getBalanceIds()
    }

    override suspend fun getBalance(symbolParam: String): BalanceEntity? {
        return balanceDao.getBalance(symbolParam)
    }

    override suspend fun deleteBalance(balanceId: Int) {
        balanceDao.deleteBalance(balanceId)
    }

    override suspend fun deleteAllBalances() {
        balanceDao.deleteAllBalances()
    }
}
