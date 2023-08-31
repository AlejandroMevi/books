package com.example.books.ui.home.fragments.Books.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.core.ApiResponceStatus
import com.example.books.ui.home.fragments.Books.data.BooksResponse
import com.example.books.ui.home.fragments.Books.data.BooksService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val respository = BooksService()

    var status = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set

    var data = MutableLiveData<BooksResponse?>(null)
        private set

    fun getBooks(
        search: String,
        startIndex: Int,
        maxResults: Int
    ) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.search(search, startIndex, maxResults)
            if (responce is ApiResponceStatus.Success) {
                data.value = responce.data
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
}