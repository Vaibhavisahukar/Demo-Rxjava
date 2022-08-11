package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.database.UserDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "UserDatabase").build()
    }

    @Provides
    @Singleton
    fun providesDataDao(database: UserDatabase) = database.getDataDao()

}
