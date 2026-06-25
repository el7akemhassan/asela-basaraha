package com.aselab.basaraha.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Setup : Screen("setup")
    data object Game : Screen("game")
    data object Questions : Screen("questions")
    data object Settings : Screen("settings")
    data object About : Screen("about")
}
