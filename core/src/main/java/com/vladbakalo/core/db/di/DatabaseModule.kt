package com.vladbakalo.core.db.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vladbakalo.core.db.AppDatabase
import com.vladbakalo.core.db.dao.AlarmDistanceDao
import com.vladbakalo.core.db.dao.LocationAlarmDao
import com.vladbakalo.core.di.CoreScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @CoreScope
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, "LocationAlarmDatabase")
            .addCallback(object : RoomDatabase.Callback(){
            })
            .build()
    }

    @CoreScope
    @Provides
    fun provideLocationAlarmDao(database: AppDatabase): LocationAlarmDao{
        return database.getLocationAlarmDao()
    }

    @CoreScope
    @Provides
    fun provideAlarmDistanceDao(database: AppDatabase): AlarmDistanceDao{
        return database.getAlarmDistanceDao()
    }
}