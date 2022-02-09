package com.amandeep.hiltsample.hilt.interfaceproblemwithinject

import javax.inject.Inject

/**
 * this one is problem with constructor injection in hilt ,like if you want to get the items in scope so we are getting complied time error.
 * so we have @Provide and @Binds to way to get the dependency.
 * Lest's see we are create a module that responsible to provide the dependency for this.
 * */

class Vehicle
@Inject
constructor(private val engine:Engine){

    fun getVehicleDetails():String="Vehicle Details are:  ${engine.getEngine()} and having ${engine.getWheel()}"
}