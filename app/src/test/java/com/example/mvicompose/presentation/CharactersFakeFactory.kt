package com.example.mvicompose.presentation

import arrow.core.NonEmptyList
import arrow.core.none
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.model.Gender
import com.example.mvicompose.domain.model.Name
import com.example.mvicompose.domain.model.Status
import com.example.mvicompose.presentation.model.UiCharacter
import java.util.*

fun makeFakeCharacters(): NonEmptyList<Character> {
    return NonEmptyList.fromListUnsafe((0..3).map { makeFakeCharacter() })
}

private fun makeFakeCharacter(): Character {
    return Character(
        id = UUID.randomUUID().toString(),
        name = Name(""),
        status = Status.of("Alive"),
        type = "type",
        species = "species",
        gender = Gender.of("Genderless"),
        urlImage = none()
    )
}

fun makeFakeUiCharacters(): List<UiCharacter> {
    return (0..3).map { makeFakeUiCharacter() }
}

private fun makeFakeUiCharacter(): UiCharacter {
    return UiCharacter(
        id = UUID.randomUUID().toString(),
        name = "Name",
        status = "Alive",
        type = "type",
        species = "species",
        gender = "Genderless",
        urlImage = ""
    )
}