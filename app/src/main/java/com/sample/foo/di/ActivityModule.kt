package com.sample.foo.di

import com.sample.data.api.ExchangeRateWebservice
import com.sample.data.api.WebserviceFactory
import com.sample.data.repository.ExchangeRateRepository
import com.sample.data.repository.ExchangeRateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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

}
