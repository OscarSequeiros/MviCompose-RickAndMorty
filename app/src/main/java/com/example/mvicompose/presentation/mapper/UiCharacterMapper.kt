package com.example.mvicompose.presentation.mapper

import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.presentation.model.UiCharacter

class UiCharacterMapper {

    fun map(characters: List<Character>): List<UiCharacter> {
        return characters.map(::map)
    }

    private fun map(character: Character): UiCharacter {
        return UiCharacter(
            id = character.id,
            name = character.name,
            status = character.status,
            type = character.type,
            species = character.species,
            gender = character.gender,
            urlImage = character.urlImage
        )
    }
}