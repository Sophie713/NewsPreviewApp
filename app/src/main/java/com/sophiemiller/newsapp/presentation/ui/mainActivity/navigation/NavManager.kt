package com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation

import androidx.navigation.NavController

class NavManager(private val navController: NavController) {

    fun navigate(destination: Screens) {
        navController.navigate(destination.route)
    }
}