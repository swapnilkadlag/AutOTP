package com.sk.autotp.di

import android.content.Context
import androidx.room.Room
import com.sk.autotp.data.AutOTPDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDb(@ApplicationContext application: Context): AutOTPDatabase {
        return Room.databaseBuilder(
            application,
            AutOTPDatabase::class.java,
            "AutOTP_db",
        ).build()
    }
}