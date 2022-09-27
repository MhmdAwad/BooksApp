package com.mhmdawad.booksapp.discover_books_screen.domain.use_case

import com.mhmdawad.booksapp.R
import com.mhmdawad.booksapp.common.utils.Resource
import com.mhmdawad.booksapp.common.utils.ResourceHandler
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import com.mhmdawad.booksapp.discover_books_screen.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllBooksUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
    private val resourceHandler: ResourceHandler
) {
    operator fun invoke(): Flow<Resource<List<BooksModelEntity>>> = flow{
        emit(Resource.Loading())
        val result = booksRepository.getBooksData()
        if(result.isNotEmpty()){
            emit(Resource.Success(result))
        }else{
            emit(Resource.Error(resourceHandler.getString(R.string.an_error_occurred)))
        }
        emit(Resource.Idle())
    }
}