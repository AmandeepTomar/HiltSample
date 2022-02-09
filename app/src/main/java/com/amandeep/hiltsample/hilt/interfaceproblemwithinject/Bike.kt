package com.amandeep.hiltsample.hilt.interfaceproblemwithinject

import javax.inject.Inject

class Bike @Inject constructor() :Engine {

    override fun getEngine(): String {
        return "Bike has 300cc Engine"
    }

    override fun getWheel(): String {
        return "Has two Wheels"
    }
}