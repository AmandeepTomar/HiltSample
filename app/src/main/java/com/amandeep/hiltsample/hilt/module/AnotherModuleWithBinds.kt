package com.amandeep.hiltsample.hilt.module

import com.amandeep.hiltsample.hilt.interfaceproblemwithinject.Car
import com.amandeep.hiltsample.hilt.interfaceproblemwithinject.Engine
import com.amandeep.hiltsample.hilt.interfaceproblemwithinject.Vehicle
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnotherModuleWithBinds {

    @Binds
    @Singleton
    abstract fun bindVehicleDependency(car: Car):Engine
}