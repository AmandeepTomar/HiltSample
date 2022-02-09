package com.amandeep.hiltsample.hilt.demo

import com.amandeep.hiltsample.hilt.Person
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDataBase(): Person {
        return Person()
    }
}