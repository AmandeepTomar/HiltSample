package com.amandeep.hiltsample.nonHiltClass

import javax.inject.Inject

class Item @Inject constructor()  {

    fun getItemName():String="ItemName"
}