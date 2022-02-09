package com.amandeep.hiltsample.hilt.demo

import com.amandeep.hiltsample.hilt.Person
import com.google.gson.annotations.Since
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataBase(): Person {
        return Person()
    }
}