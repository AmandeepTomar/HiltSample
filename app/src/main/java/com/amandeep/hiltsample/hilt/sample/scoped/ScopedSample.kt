package com.amandeep.hiltsample.hilt.sample.scoped

import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScopedSample @Inject constructor(){

     fun getScopedFunction():String="Singleton scoped function"

}

@ActivityScoped
class ActivityScopedSample @Inject constructor(){

     fun getScopedFunction(): String {
        return "Activity Scoped function"
    }
}

@FragmentScoped
class FragmentScopedSample @Inject constructor(){

    fun getScopedFunction(): String {
        return "FragmentScoped function"
    }
}