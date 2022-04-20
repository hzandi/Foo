package com.sample.data.db.repository

import com.sample.data.db.dao.LatestExRatesDao
import com.sample.data.entity.LatestExRateEntity
import javax.inject.Inject

class LocalLatestExRatesRepositoryImpl
@Inject constructor(
    private val latestExRatesDao: LatestExRatesDao,
) : LocalLatestExRatesRepository {

    override suspend fun insertLatestExchangeRates(latestExRateEntity: LatestExRateEntity) {
        latestExRatesDao.insertOrUpdateLatestRates(latestExRateEntity)
    }

    override suspend fun getLatestExchangeRates(): LatestExRateEntity? {
        val latestExRateEntities = latestExRatesDao.getLatestExRates() ?: ArrayList()
        return if(latestExRateEntities.isNotEmpty()) {
            latestExRateEntities[0]
        }else{
            null
        }
    }

    override suspend fun deleteLatestExchangeRates() {
        latestExRatesDao.deleteAllCatalogs()
    }
}
