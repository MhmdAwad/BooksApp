package com.mhmdawad.booksapp.discover_books_screen.domain.repository

import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity

interface BooksRepository {

    suspend fun getBooksData(): List<BooksModelEntity>
    suspend fun cacheBooksData(books: List<BooksModelEntity>)

}