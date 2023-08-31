package com.example.books.ui.home

data class BooksInfo(
    var id: String? = null,
    var title: String? = null,
    var authors: List<String>? = null,
    var description: String? = null,
    var smallThumbnail: String? = null,
    var thumbnail: String? = null,
    var canonicalVolumeLink: String? = null,
    var infoLink: String? = null,
    var previewLink: String? = null
)
