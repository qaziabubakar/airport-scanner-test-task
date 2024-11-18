package com.vorctis.devgate.task2.utils

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Next : Routes("next")
}