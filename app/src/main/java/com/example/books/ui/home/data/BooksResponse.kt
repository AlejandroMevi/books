package com.example.books.ui.home.data

data class BooksResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<BookItem>
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val description: String,
    val imageLinks: ImageLinks
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
)