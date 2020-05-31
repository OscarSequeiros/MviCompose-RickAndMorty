package com.example.mvicompose.presentation

import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.mvibase.MviResult

sealed class CharacterResult : MviResult {

    sealed class LoadAllResult: CharacterResult() {
        object Loading : LoadAllResult()
        class FilledCharacterList(val characters: List<Character>) : LoadAllResult()
        object EmptyCharacterList : LoadAllResult()
        class Failure(val error: Throwable) : LoadAllResult()
    }
}