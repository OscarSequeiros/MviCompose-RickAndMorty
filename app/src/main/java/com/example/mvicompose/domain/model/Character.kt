package com.example.mvicompose.domain.model

import arrow.core.Option

data class Character(
    val id: String,
    val name: Name,
    val status: Status,
    val type: String,
    val species: String,
    val gender: Gender,
    val urlImage: Option<String>
)