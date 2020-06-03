package com.example.mvicompose.presentation.mapper

import arrow.core.NonEmptyList
import arrow.core.getOrElse
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.model.Gender
import com.example.mvicompose.domain.model.Status
import com.example.mvicompose.presentation.model.UiCharacter

class UiCharacterMapper {

    fun map(characters: NonEmptyList<Character>): List<UiCharacter> {
        return characters.map(::map).toList()
    }

    private fun map(character: Character): UiCharacter {
        return UiCharacter(
            id = character.id,
            name = character.name.value,
            status = map(character.status),
            type = character.type,
            species = character.species,
            gender = map(character.gender),
            urlImage = character.urlImage.getOrElse { "" }
        )
    }

    private fun map(gender: Gender): String {
        return when (gender) {
            Gender.Female -> "Female"
            Gender.Male -> "Male"
            Gender.Genderless -> "Genderless"
            Gender.Unknown -> "-"
        }
    }

    private fun map(status: Status): String {
        return when (status) {
            Status.Alive -> "Breathing"
            Status.Dead -> "Deceased"
            Status.Unknown -> "Unknown"
        }
    }
}