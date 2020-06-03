package com.example.mvicompose.data

import arrow.core.*
import arrow.syntax.collections.flatten
import com.example.mvicompose.data.remote.model.RemoteCharacter
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.model.Gender
import com.example.mvicompose.domain.model.Name
import com.example.mvicompose.domain.model.Status

class DataCharacterMapper {

    fun map(characters: List<RemoteCharacter>): List<Character> {
        return characters.map(::map).filter { it.isDefined() }.flatten()
    }

    private fun map(character: RemoteCharacter): Option<Character> {

        return when (val name = Name.of(character.name)) {
            is Some -> Character(
                id = character.id,
                name = name.t,
                status = Status.of(character.status),
                type = character.type,
                species = character.species,
                gender = Gender.of(character.gender),
                urlImage = Option.fromNullable(character.image)
            ).some()
            is None -> none()
        }
    }
}