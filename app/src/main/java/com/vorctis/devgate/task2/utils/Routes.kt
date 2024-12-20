package com.vorctis.devgate.task2.utils

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object MainListScreen : Routes("main_list_screen")
    object SearchBagTagScreen : Routes("search_bagtag_screen")
    object ScannerScreen : Routes("scanner_screen")
    object LocationSelectedScreen : Routes("location_selected_screen")
}