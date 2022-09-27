package com.mhmdawad.booksapp.discover_books_screen.presentation

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.mhmdawad.booksapp.R
import com.mhmdawad.booksapp.book_detail_screen.presentation.getDetailRoute
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import com.mhmdawad.booksapp.ui.theme.darkBlue
import com.mhmdawad.booksapp.ui.theme.lightBlue


@Composable
@ExperimentalFoundationApi
fun DiscoverScreen(
    viewModel: BooksViewModel = hiltViewModel(),
    navController: NavController
) {
    val booksList by remember { viewModel.booksListState }
    val isLoading by remember { viewModel.isLoading }
    val errorOccurred by remember { viewModel.errorOccurred }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    item {
                        Text(
                            text = "Explore thousands of books",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier
                                .padding(top = 40.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    item {
                        SearchBox(
                            modifier = Modifier
                                .padding(horizontal = 15.dp),
                            onSearch = { searchQuery ->
                                viewModel.searchForBook(searchQuery)
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    item {
                        Text(
                            text = "Discover Books",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    items(booksList.size) { index ->
                        BookItemEntity(
                            modifier = Modifier
                                .height(180.dp)
                                .fillMaxWidth()
                                .padding(8.dp)
                                .shadow(4.dp, RoundedCornerShape(8.dp))
                                .background(Color.White),
                            booksList[index],
                            navController
                        )
                    }
                }
            )
            if (isLoading)
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Center
                ) {
                    CircularProgressIndicator()
                }

            if (errorOccurred.isNotBlank())
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Center
                ) {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Text(
                            text = errorOccurred,
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier
                            .background(lightBlue, RoundedCornerShape(14.dp))
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                viewModel.getBooksList()
                            }
                            .padding(vertical = 6.dp, horizontal = 12.dp)) {
                            Text(
                                text = stringResource(id = R.string.try_again),
                                color = darkBlue,

                                )
                        }
                    }
                }

        }
    }
}


@Composable
fun BookItemEntity(
    modifier: Modifier = Modifier,
    book: BooksModelEntity,
    navController: NavController
) {
    Box(
        modifier = modifier
            .clickable {
                val json = Uri.encode(Gson().toJson(book))
                navController.navigate(
                    getDetailRoute(json)
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.imageLink)
                    .crossfade(true)
                    .build(),
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(10.dp)),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.scale(0.4f)
                    )
                },
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "By ${book.author}",
                    color = Color.Gray,
                    fontSize = 12.sp,
                )
                Column {

                    Text(
                        text = book.title,
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_pages),
                            contentDescription = "Pages",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = book.pages.toString(),
                            color = Color.DarkGray,
                            fontSize = 11.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(lightBlue)
                        .padding(vertical = 2.dp, horizontal = 10.dp)
                ) {
                    Text(
                        text = book.language,
                        color = darkBlue,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }
    var isHintDisplayed by remember { mutableStateOf(true) }

    Box(modifier = modifier) {
        BasicTextField(
            modifier = Modifier
                .align(Center)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.hasFocus
                },
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                onSearch(it)
            },
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
            ),
        )

        if (isHintDisplayed)
            Text(
                text = "Search for books...",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Center)
                    .padding(horizontal = 8.dp),
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

            )
    }
}
