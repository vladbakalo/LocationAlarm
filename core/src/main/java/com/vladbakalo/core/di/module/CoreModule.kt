package com.vladbakalo.core.di.module

import com.vladbakalo.core.di.CoreScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class CoreModule {

    @CoreScope
    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher{
        return Dispatchers.IO
    }
}