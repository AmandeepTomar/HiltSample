package com.amandeep.hiltsample.hilt

import android.util.Log
import javax.inject.Inject

class Car @Inject constructor() {

    fun getCar(){
        Log.d("Car", "getCar: car is running...")
    }
}