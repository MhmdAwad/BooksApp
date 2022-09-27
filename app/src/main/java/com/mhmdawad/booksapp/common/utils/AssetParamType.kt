package com.mhmdawad.booksapp.common.utils

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity

class AssetParamType : NavType<BooksModelEntity>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): BooksModelEntity? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): BooksModelEntity {
        return Gson().fromJson(value, BooksModelEntity::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: BooksModelEntity) {
        bundle.putParcelable(key, value)
    }
}