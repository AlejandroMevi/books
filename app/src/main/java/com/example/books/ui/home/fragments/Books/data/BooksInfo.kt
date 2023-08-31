package com.example.books.ui.home.fragments.Books.data

data class BooksInfo(
    var id: String? = null,
    var title: String? = null,
    var authors: List<String>? = null,
    var subtitle: String? = null,
    var publisher: String? = null,
    var publishedDate: String? = null,
    var description: String? = null,
    var smallThumbnail: String? = null,
    var thumbnail: String? = null,
    var canonicalVolumeLink: String? = null,
    var infoLink: String? = null,
    var previewLink: String? = null
)
