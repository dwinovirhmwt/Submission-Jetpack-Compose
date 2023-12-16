package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.model

data class AnimeGhibli(
    val id: Int,
    val title: String,
    val poster: Int,
    val year: String,
    val genre: String,
    val producer: String,
    val score: String,
    val synopsis: String,
    var isFavorite: Boolean = false
)
