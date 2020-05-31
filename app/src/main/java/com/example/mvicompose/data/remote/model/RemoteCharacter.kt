package com.example.mvicompose.data.remote.model

data class RemoteCharacter(
    val id: String,
    val name: String,
    val status: String,
    val type: String,
    val species: String,
    val gender: String,
    val image: String?
)