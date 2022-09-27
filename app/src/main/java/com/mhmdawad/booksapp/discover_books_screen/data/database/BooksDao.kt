package com.mhmdawad.booksapp.discover_books_screen.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity

@Dao
interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheBooks(books: List<BooksModelEntity>)

    @Query("DELETE FROM books_table")
    suspend fun clearCaches()

    @Query("SELECT * FROM books_table")
    suspend fun getAllCachedBooks(): List<BooksModelEntity>
}