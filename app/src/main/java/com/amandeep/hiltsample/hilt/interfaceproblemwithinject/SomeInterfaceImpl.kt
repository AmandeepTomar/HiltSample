package com.amandeep.hiltsample.hilt.interfaceproblemwithinject

import javax.inject.Inject

class SomeDependency @Inject constructor(private val someInterface: SomeInterface){

    fun getDependencyDetails(): String {
        return someInterface.getDetails()
    }
}


class SomeInterfaceImpl
@Inject
constructor():SomeInterface{

    override fun getDetails(): String = "Some Interface in Impl"
}


interface SomeInterface{

    fun getDetails():String
}