package com.mhmdawad.booksapp.discover_books_screen.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmdawad.booksapp.common.utils.Resource
import com.mhmdawad.booksapp.discover_books_screen.domain.model.BooksModelEntity
import com.mhmdawad.booksapp.discover_books_screen.domain.use_case.GetAllBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase
) : ViewModel() {

    private val _booksListState = mutableStateOf<List<BooksModelEntity>>(listOf())
    val booksListState: State<List<BooksModelEntity>> = _booksListState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorOccurred = mutableStateOf("")
    val errorOccurred: State<String> = _errorOccurred

    init {
        getBooksList()
    }

    private fun getBooksList() {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = getAllBooksUseCase()) {
                is Resource.Success -> _booksListState.value = result.data ?: emptyList()
                is Resource.Error -> _errorOccurred.value = result.msg ?: ""
                is Resource.Loading -> _isLoading.value = true
                is Resource.Idle -> _isLoading.value = false
            }
        }
    }
}