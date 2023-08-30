package com.example.books.ui.home.data

import com.example.books.core.ApiResponceStatus
import com.example.books.core.RetrofitConnection
import com.example.books.core.evaluateResponce
import com.example.books.core.makeNetworkCall

class BooksService {

    val autservice = RetrofitConnection().getRetrofit()

    suspend fun search(search: String): ApiResponceStatus<BooksResponse> {
        return makeNetworkCall {
            val response = autservice.create(BooksApiClient::class.java).searchBooks(search)
            //evaluateResponce(response.codigo.toString())
            response
        }
    }
}