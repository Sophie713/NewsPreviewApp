package com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation

import androidx.navigation.NavController

class NavManager(private val navController: NavController) {

    fun navigate(destination: Screens) {
        navController.navigate(destination.route)
    }

    fun navigateWithArgs(destination: Screens, vararg args: String) {
        val routeWithArgs = buildString {
            append(destination.route)
            args.forEach {
                append("/$it")
            }
        }
        navController.navigate(routeWithArgs)
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}