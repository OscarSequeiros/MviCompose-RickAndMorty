package com.example.mvicompose

import arrow.core.NonEmptyList
import arrow.core.Some
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.model.Gender
import com.example.mvicompose.domain.model.Name
import com.example.mvicompose.domain.model.Status
import com.example.mvicompose.presentation.model.UiCharacter

object CharactersFactory {

    fun makeFakeCharacters(n: Int = 4): List<Character> =
        (0..n).map { makeFakeCharacter() }

    private fun makeFakeCharacter() =
        Character(
            id = RandomFactory.generateRandomString(3),
            name = Name(RandomFactory.generateRandomString(10)),
            status = Status.of(RandomFactory.generateRandomString(5)),
            type = RandomFactory.generateRandomString(),
            species = RandomFactory.generateRandomString(),
            gender = Gender.of(RandomFactory.generateRandomString(5)),
            urlImage = Some(RandomFactory.generateRandomString())
        )

    fun makeFakeNonEmptyCharacters(): NonEmptyList<Character> {
        return NonEmptyList.fromListUnsafe(makeFakeCharacters())
    }

    fun makeFakeUiCharacters(): List<UiCharacter> {
        return (0..3).map { makeFakeUiCharacter() }
    }

    private fun makeFakeUiCharacter(): UiCharacter {
        return UiCharacter(
            id = RandomFactory.generateRandomString(3),
            name = RandomFactory.generateRandomString(10),
            status = "Alive",
            type = RandomFactory.generateRandomString(),
            species = "species",
            gender = "Genderless",
            urlImage = RandomFactory.generateRandomString()
        )
    }
}