package com.example.books.ui.home.data

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BooksApiClient {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int
    ): BooksResponse
}