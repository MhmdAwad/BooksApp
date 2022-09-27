package com.mhmdawad.booksapp.discover_books_screen.domain.use_case

import com.mhmdawad.booksapp.R
import com.mhmdawad.booksapp.common.utils.Resource
import com.mhmdawad.booksapp.common.utils.ResourceHandler
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import com.mhmdawad.booksapp.discover_books_screen.domain.repository.BooksRepository
import javax.inject.Inject

class GetAllBooksUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
    private val resourceHandler: ResourceHandler
) {
    suspend operator fun invoke(): Resource<List<BooksModelEntity>> {
        val result = booksRepository.getBooksData()
        return if(result.isNotEmpty()){
            Resource.Success(result)
        }else{
            Resource.Error(resourceHandler.getString(R.string.an_error_occurred))
        }
    }
}