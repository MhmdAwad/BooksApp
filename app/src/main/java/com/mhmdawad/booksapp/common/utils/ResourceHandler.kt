package com.mhmdawad.booksapp.common.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getString(stringID: Int): String =
        context.resources.getString(stringID)

}