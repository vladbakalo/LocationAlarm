package com.vladbakalo.location_alarm.di.module

import com.google.firebase.auth.FirebaseAuth
import com.vladbakalo.location_alarm.manager.AuthManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
}