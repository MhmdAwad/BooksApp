package com.mhmdawad.booksapp.discover_books_screen.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmdawad.booksapp.R
import com.mhmdawad.booksapp.common.utils.Resource
import com.mhmdawad.booksapp.common.utils.ResourceHandler
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import com.mhmdawad.booksapp.discover_books_screen.domain.use_case.GetAllBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val resourceHandler: ResourceHandler
) : ViewModel() {

    private val _booksList = mutableListOf<BooksModelEntity>()

    private val _booksListState = mutableStateOf<List<BooksModelEntity>>(listOf())
    val booksListState: State<List<BooksModelEntity>> = _booksListState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorOccurred = mutableStateOf("")
    val errorOccurred: State<String> = _errorOccurred

    init {
        getBooksList()
    }

    fun getBooksList() {
        _errorOccurred.value = ""
        getAllBooksUseCase()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _booksList.addAll(result.data ?: emptyList())
                        _booksListState.value = _booksList
                    }
                    is Resource.Error -> _errorOccurred.value = result.msg ?: ""
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Idle -> _isLoading.value = false
                }
            }.launchIn(viewModelScope)
    }


    fun searchForBook(searchQuery: String) {
        _isLoading.value = true
        if (searchQuery.isEmpty()) {
            _booksListState.value = _booksList
        } else {
            _booksListState.value = _booksList.filter {
                it.title.contains(searchQuery, true) ||
                        it.author.contains(searchQuery, true)
            }
        }
        _errorOccurred.value = if (_booksListState.value.isEmpty())
            resourceHandler.getString(R.string.no_book_found)
        else ""

        _isLoading.value = false
    }
}