package com.sample.data.api

import com.sample.data.BuildConfig
import com.sample.data.pref.LocalStorage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WebserviceFactory @Inject constructor(private val localStorage: LocalStorage) {

    private companion object {
        const val API_BASE_URL = "http://api.exchangeratesapi.io/"
        const val ACCESS_KEY_AUTHORIZATION = "b657a34f618eb48d3284f822d643a2d3"
    }

    fun <S> createService(serviceClass: Class<S>): S =
        retrofitApi.create(serviceClass)

    private val retrofitApi = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build()

    private fun getOkHttpClient(): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor()
                .apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
                .let {
                    okHttpBuilder.addInterceptor(it)
                }
        }

        return okHttpBuilder.build()

    }

    private inner class AuthorizationInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("access_key", ACCESS_KEY_AUTHORIZATION)
                .build()

            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)

            val request: Request = requestBuilder.build()
            return chain.proceed(request)
        }

    }

}
