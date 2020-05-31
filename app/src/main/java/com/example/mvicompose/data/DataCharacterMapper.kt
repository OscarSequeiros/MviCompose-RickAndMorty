package com.example.mvicompose.data

import com.example.mvicompose.data.remote.model.RemoteCharacter
import com.example.mvicompose.domain.model.Character

class DataCharacterMapper {

    fun map(characters: List<RemoteCharacter>): List<Character> {
        return characters.map(::map)
    }

    private fun map(character: RemoteCharacter): Character {
        return Character(
            id = character.id,
            name = character.name,
            status = character.status,
            type = character.type,
            species = character.species,
            gender = character.gender,
            urlImage = character.image ?: ""
        )
    }
}