package com.mhmdawad.booksapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mhmdawad.booksapp.book_detail_screen.presentation.DetailScreen
import com.mhmdawad.booksapp.book_detail_screen.presentation.getDetailRoute
import com.mhmdawad.booksapp.common.utils.AssetParamType
import com.mhmdawad.booksapp.common.utils.Constants
import com.mhmdawad.booksapp.common.utils.Screens
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import com.mhmdawad.booksapp.discover_books_screen.presentation.DiscoverScreen
import com.mhmdawad.booksapp.splash_screen.SplashScreen
import com.mhmdawad.booksapp.ui.theme.BooksAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screens.SplashScreen.route
                ) {
                    composable(route = Screens.SplashScreen.route){
                        SplashScreen(navController = navController)
                    }
                    composable(route = Screens.DiscoverScreen.route) {
                        DiscoverScreen(navController = navController)
                    }
                    composable(
                        route = getDetailRoute(),
                        arguments = listOf(
                            navArgument(Constants.BOOK_ARGUMENT_MODEL) {
                                type = AssetParamType()
                            }
                        )
                    ) {
                        val bookModel = remember {
                            if (Build.VERSION.SDK_INT >= 33) {
                                it.arguments?.getParcelable(
                                    Constants.BOOK_ARGUMENT_MODEL,
                                    BooksModelEntity::class.java
                                )
                            } else {
                                it.arguments?.getParcelable(Constants.BOOK_ARGUMENT_MODEL)
                            }
                        }
                        bookModel?.let {
                            DetailScreen(it, navController)
                        }
                    }
                }
            }
        }
    }
}
