package com.example.mvicompose.domain.model

data class Character(
    val id: String,
    val name: String,
    val status: String,
    val type: String,
    val species: String,
    val gender: String,
    val urlImage: String
)