package com.vorctis.devgate.task2.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyUtils @Inject constructor() {
    fun doSomething(): String {
        return "name"
    }
}