package com.example.mvicompose.presentation

import arrow.core.NonEmptyList
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.mvibase.MviResult

sealed class CharacterResult : MviResult {

    sealed class LoadAllResult: CharacterResult() {
        object Loading : LoadAllResult()
        class FilledCharacterList(val characters: NonEmptyList<Character>) : LoadAllResult()
        object EmptyCharacterList : LoadAllResult()
        class Failure(val error: Throwable) : LoadAllResult()
    }
}