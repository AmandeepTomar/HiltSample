package com.amandeep.hiltsample.hilt.interfaceproblemwithinject

import javax.inject.Inject

class Car @Inject constructor() :Engine {

    override fun getEngine(): String {
        return "car Engine with 1000hp"
    }

    override fun getWheel(): String {
        return "Has 4 wheels"
    }
}