package com.amandeep.hiltsample.hilt.interfaceproblemwithinject

import javax.inject.Inject
import javax.inject.Qualifier


interface IGetThing{
    fun getThing():String
}

class GetThingImpl1
@Inject
constructor():IGetThing{
    override fun getThing(): String {
        return "GetThing1"
    }
}

class GetThingImpl2
@Inject
constructor():IGetThing{
    override fun getThing(): String {
        return "GetThing2"
    }
}


class ObjectWithSameReturnType
@Inject
constructor(@Impl1 private val implObject1: IGetThing, @Impl2 private val implObject2: IGetThing){

    fun getImpl1():String=implObject1.getThing()
    fun getImpl2():String=implObject2.getThing()

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2