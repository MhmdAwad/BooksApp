package com.mhmdawad.booksapp.discover_books_screen.domain.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mhmdawad.booksapp.common.utils.Constants
import kotlinx.parcelize.Parcelize

@Entity(tableName = Constants.BOOKS_TABLE_NAME)
@Parcelize
data class BooksModelEntity(
    var author: String,
    var country: String,
    var imageLink: String,
    var language: String,
    var link: String,
    var pages: Int,
    @PrimaryKey
    var title: String,
    var year: Int
): Parcelable