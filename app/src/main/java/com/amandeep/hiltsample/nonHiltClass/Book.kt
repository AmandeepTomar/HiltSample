package com.amandeep.hiltsample.nonHiltClass

import javax.inject.Inject


class Book @Inject constructor() {

    fun getBookName():String="BookName"
}