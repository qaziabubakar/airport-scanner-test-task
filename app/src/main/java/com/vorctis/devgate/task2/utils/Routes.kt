package com.vorctis.devgate.task2.utils

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object MainListScreen : Routes("main_list_screen")
}