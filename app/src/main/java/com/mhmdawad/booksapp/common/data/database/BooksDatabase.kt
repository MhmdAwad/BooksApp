package com.mhmdawad.booksapp.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhmdawad.booksapp.discover_books_screen.data.database.BooksDao
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity

@Database(
    entities = [BooksModelEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BooksDatabase: RoomDatabase() {

    abstract fun booksDao(): BooksDao
}