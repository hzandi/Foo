package com.sample.foo.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ActivityModule {

//    @Provides
//    fun provideGiphyWebService(webserviceFactory: WebserviceFactory): GiphyWebservice {
//        return webserviceFactory.createService(GiphyWebservice::class.java)
//    }
//
//    @Provides
//    fun provideGiphyRepository(giphyRepositoryImpl: GiphyRepositoryImpl): GiphyRepository {
//        return giphyRepositoryImpl
//    }

}
