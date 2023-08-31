package com.example.books.ui.home.fragments.Books.data

data class BooksResponse(
    val kind: String? = null,
    val totalItems: Int? = null,
    val items: List<BookItem>? = null
)

data class BookItem(
    val id: String? = null,
    val volumeInfo: VolumeInfo? = null
)

data class VolumeInfo(
    val title: String? = null,
    val subtitle: String? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val authors: List<String>? = null,
    val description: String? = null,
    val imageLinks: ImageLinks? = null,
    val canonicalVolumeLink: String? = null,
    val infoLink: String? = null,
    val previewLink: String? = null
    )

data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)