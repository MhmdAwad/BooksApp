package com.mhmdawad.booksapp.discover_books_screen.data.remote

import com.mhmdawad.booksapp.discover_books_screen.data.remote.dto.BooksResponseModel
import retrofit2.http.GET

interface BooksApi {

    @GET("/booksApp/books")
    suspend fun getBooks(): BooksResponseModel

}