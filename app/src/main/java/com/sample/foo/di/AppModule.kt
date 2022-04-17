package com.sample.foo.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.sample.foo.common.DeviceUtils

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        deviceUtils: DeviceUtils
    ): SharedPreferences {
        return if (deviceUtils.isApiLevelAtLeast(Build.VERSION_CODES.M)) {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
            val fileName = "secrets"
            val keyEncryptionScheme = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
            val valueEncryptionScheme =
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            EncryptedSharedPreferences.create(
                fileName,
                masterKeyAlias,
                context,
                keyEncryptionScheme,
                valueEncryptionScheme
            )
        } else {
            PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

}
