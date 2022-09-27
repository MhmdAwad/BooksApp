package com.mhmdawad.booksapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mhmdawad.booksapp.common.utils.AssetParamType
import com.mhmdawad.booksapp.common.utils.Constants
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import com.mhmdawad.booksapp.discover_books_screen.presentation.DiscoverScreen
import com.mhmdawad.booksapp.ui.theme.BooksAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Constants.DISCOVER_SCREEN
                ) {
                    composable(route = Constants.DISCOVER_SCREEN) {

                    }
                    composable(
                        route = Constants.DETAIL_SCREEN,
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
                    }
                }
            }
        }
    }
}