package com.sample.foo.di

import android.content.Context
import com.sample.data.api.ExchangeRateWebservice
import com.sample.data.api.WebserviceFactory
import com.sample.data.api.repository.ExchangeRateRepository
import com.sample.data.api.repository.ExchangeRateRepositoryImpl
import com.sample.data.db.FooDatabase
import com.sample.data.db.dao.BalanceDao
import com.sample.data.db.dao.LatestExRatesDao
import com.sample.data.db.repository.LocalBalanceRepository
import com.sample.data.db.repository.LocalBalanceRepositoryImpl
import com.sample.data.db.repository.LocalLatestExRatesRepository
import com.sample.data.db.repository.LocalLatestExRatesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object ActivityModule {

    @Provides
    fun provideGiphyWebService(webserviceFactory: WebserviceFactory): ExchangeRateWebservice {
        return webserviceFactory.createService(ExchangeRateWebservice::class.java)
    }

    @Provides
    fun provideGiphyRepository
                (exchangeRateRepositoryImpl: ExchangeRateRepositoryImpl): ExchangeRateRepository {
        return exchangeRateRepositoryImpl
    }

    @Provides
    fun provideFooDatabase(
        @ApplicationContext context: Context
    ): FooDatabase {
        return FooDatabase.getInstance(context)
    }

    @Provides
    fun provideLatestExRatesDao(
        @ApplicationContext context: Context
    ): LatestExRatesDao {
        return FooDatabase.getInstance(context).latestExRatesDao()
    }

    @Provides
    fun provideBalanceDao(
        @ApplicationContext context: Context
    ): BalanceDao {
        return FooDatabase.getInstance(context).balanceDao()
    }

    @Provides
    fun provideLocalLatestExRatesRepository(
        localLatestExRatesRepositoryImpl: LocalLatestExRatesRepositoryImpl
    ): LocalLatestExRatesRepository {
        return localLatestExRatesRepositoryImpl
    }

    @Provides
    fun provideLocalBalanceRepository(
        localBalanceRepositoryImpl: LocalBalanceRepositoryImpl
    ): LocalBalanceRepository {
        return localBalanceRepositoryImpl
    }

}
