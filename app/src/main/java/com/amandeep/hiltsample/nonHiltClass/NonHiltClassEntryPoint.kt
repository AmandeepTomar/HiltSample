package com.amandeep.hiltsample.nonHiltClass

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class NonHiltClassEntryPoint @Inject constructor(){

    @EntryPoint
    @InstallIn(ActivityComponent::class)
     interface NonHiltClassEntrypointInterface{
         fun getBook():Book
         fun getItem():Item
     }


}