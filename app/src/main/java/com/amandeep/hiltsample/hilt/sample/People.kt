package com.amandeep.hiltsample.hilt.sample

import javax.inject.Inject

class People @Inject constructor(private val address: Address) {

    fun getPeopleInformation():String="We have peoples information"

    fun getPeopleAddress():String=address.getAddressDetails()

}

