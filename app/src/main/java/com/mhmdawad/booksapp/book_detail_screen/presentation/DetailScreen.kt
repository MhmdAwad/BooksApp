package com.mhmdawad.booksapp.book_detail_screen.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mhmdawad.booksapp.R
import com.mhmdawad.booksapp.common.utils.Constants
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import com.mhmdawad.booksapp.ui.theme.darkBlue
import com.mhmdawad.booksapp.ui.theme.lightBlue


@Composable
fun DetailScreen(
    book: BooksModelEntity,
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            ScreenHeader(navController)
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (image, box) = createRefs()
                Box(
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .constrainAs(box) {
                            top.linkTo(parent.top, margin = 150.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                            height = Dimension.value(210.dp)
                        }
                )

                BookDetails(
                    modifier = Modifier
                        .padding(top = 80.dp)
                        .fillMaxWidth()
                        .constrainAs(image) {
                            top.linkTo(box.top)
                            bottom.linkTo(box.top)
                            start.linkTo(box.start)
                            end.linkTo(box.end)
                        },
                    book = book
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            BookDescription(book)
            VisitBook(bookLink = book.link)
        }

    }
}

@Composable
fun BookDetails(
    modifier: Modifier = Modifier,
    book: BooksModelEntity,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.imageLink)
                .crossfade(true)
                .build(),
            contentDescription = book.title,
            contentScale = ContentScale.Crop,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.scale(0.4f)
                )
            },
            modifier = Modifier
                .height(200.dp)
                .width(140.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = book.title,
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pages),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = book.pages.toString(),
                color = Color.DarkGray,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .size(3.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.DarkGray)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = book.author,
                color = Color.DarkGray,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
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

@Composable
fun ScreenHeader(
    navController: NavController,
) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = CenterVertically
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = null,
            modifier = Modifier.clickable {
                navController.popBackStack()
            })
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.book_detail),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Center)
            )
        }
    }
}

@Composable
fun BookDescription(book: BooksModelEntity) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(20.dp)
    ) {
        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "The ${book.title} has been written by \n${book.author} in ${book.year} at ${book.country}",
            color = Color.DarkGray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun VisitBook(bookLink: String) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        contentAlignment = BottomCenter
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                Uri.parse(bookLink).let {
                    val intent = Intent(Intent.ACTION_VIEW, it)
                    context.startActivity(intent)
                }
            }) {
            Text(
                text = "Visit Book",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun getDetailRoute(argument: String? = null): String {
    return if (argument == null)
        "${Constants.DETAIL_SCREEN}/{${Constants.BOOK_ARGUMENT_MODEL}}"
    else
        "${Constants.DETAIL_SCREEN}/$argument"
}
