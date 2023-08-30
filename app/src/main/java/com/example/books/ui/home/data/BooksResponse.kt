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
    val imageLinks: ImageLinks,
    val canonicalVolumeLink: String? = null,
    val infoLink: String? = null,
    val previewLink: String? = null
    )

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
)