package com.mhmdawad.booksapp.common.utils

sealed class Screens(val route: String) {
    object SplashScreen: Screens("splash_screen")
    object DiscoverScreen: Screens("discover_screen")
    object DetailScreen: Screens("detail_screen")
}

