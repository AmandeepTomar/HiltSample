package com.amandeep.hiltsample.nonHiltClass

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface NonHiltClassEntrypointInterface{
    fun getBook():Book
    fun getItem():Item
}
