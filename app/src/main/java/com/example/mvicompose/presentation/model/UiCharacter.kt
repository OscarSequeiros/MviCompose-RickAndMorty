package com.example.mvicompose.presentation.model

data class UiCharacter(
    val id: String,
    val name: String,
    val status: String,
    val type: String,
    val species: String,
    val gender: String,
    val urlImage: String
) {

    val specieStatus: String
        get() = if (type.isNotEmpty()) {
            "$species / $status / $type" 
        } else {
            "$species / $status"
        }
}