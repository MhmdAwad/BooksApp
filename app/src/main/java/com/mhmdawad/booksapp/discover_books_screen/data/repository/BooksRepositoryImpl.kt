package com.mhmdawad.booksapp.discover_books_screen.data.repository

import com.mhmdawad.booksapp.discover_books_screen.data.database.BooksDao
import com.mhmdawad.booksapp.discover_books_screen.data.remote.BooksApi
import com.mhmdawad.booksapp.discover_books_screen.data.remote.dto.toBooksModelList
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import com.mhmdawad.booksapp.discover_books_screen.domain.repository.BooksRepository
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
    private val booksDao: BooksDao
) : BooksRepository {

    override suspend fun getBooksData(): List<BooksModelEntity> {
        val result = booksApi.getBooks()
        if (result.isNotEmpty())
            cacheBooksData(result.toBooksModelList())

        return booksDao.getAllCachedBooks()
    }

    override suspend fun cacheBooksData(books: List<BooksModelEntity>) {
        booksDao.cacheBooks(books)
    }
}