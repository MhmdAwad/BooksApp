package com.mhmdawad.booksapp.discover_books_screen.data.remote.dto


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.mhmdawad.booksapp.common.utils.Constants
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import kotlinx.parcelize.Parcelize

@Parcelize
class BooksResponseModel : ArrayList<BooksModelItem?>(), Parcelable

fun BooksResponseModel.toBooksModelList(): List<BooksModelEntity> {
    val list = mutableListOf<BooksModelEntity>()
    forEach {
        it?.let { item ->
            list.add(
                BooksModelEntity(
                    author = item.author ?: "",
                    country = item.country ?: "",
                    imageLink = "${Constants.BOOK_IMAGE_LINK}${item.imageLink}",
                    language = item.language ?: "",
                    link = item.link ?: "",
                    pages = item.pages ?: 0,
                    title = item.title ?: "",
                    year = item.year ?: 0
                )
            )
        }
    }
    return list
}

@Entity(tableName = Constants.BOOKS_TABLE_NAME)
data class BooksModelItem(
    @SerializedName("author")
    @Expose
    var author: String? = null,
    @SerializedName("country")
    @Expose
    var country: String? = null,
    @SerializedName("imageLink")
    @Expose
    var imageLink: String? = null,
    @SerializedName("language")
    @Expose
    var language: String? = null,
    @SerializedName("link")
    @Expose
    var link: String? = null,
    @SerializedName("pages")
    @Expose
    var pages: Int? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("year")
    @Expose
    var year: Int? = null
)