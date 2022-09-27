package com.mhmdawad.booksapp.book_detail_screen.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhmdawad.booksapp.R
import com.mhmdawad.booksapp.common.utils.Constants
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity

@Composable
fun DetailScreen(booksModelEntity: BooksModelEntity) {
    Column() {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
            Text(
                text = stringResource(id = R.string.book_detail),
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterVertically)
            )
        }
    }
}

fun getDetailRoute(argument: String? = null): String{
    return if(argument == null)
        "${Constants.DETAIL_SCREEN}/{${Constants.BOOK_ARGUMENT_MODEL}}"
    else
        "${Constants.DETAIL_SCREEN}/$argument"
}
