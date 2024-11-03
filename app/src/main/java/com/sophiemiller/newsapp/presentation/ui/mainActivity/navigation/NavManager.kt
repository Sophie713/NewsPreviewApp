package com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation

import androidx.navigation.NavController

class NavManager(private val navController: NavController) { //todo clear backstack

    fun navigate(destination: Screens) { //todo xyz navigate with args
        navController.navigate(destination.route)
    }
}