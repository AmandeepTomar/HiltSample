package com.amandeep.hiltsample.hilt.module

import com.amandeep.hiltsample.hilt.Person
import com.amandeep.hiltsample.hilt.interfaceproblemwithinject.*
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

    @Provides
    @Singleton
    fun getDependencyImpl():SomeInterface{
        return SomeInterfaceImpl()
    }

    @Impl1
    @Provides
    @Singleton
    fun provideIGetThingDetails1():IGetThing{
        return GetThingImpl1()
    }

    @Impl2
    @Provides
    @Singleton
    fun provideIGetThingDetails2():IGetThing{
        return GetThingImpl2()
    }


}