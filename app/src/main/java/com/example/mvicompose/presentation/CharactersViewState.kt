package com.example.mvicompose.presentation

import com.example.mvicompose.mvibase.MviViewState
import com.example.mvicompose.presentation.model.UiCharacter

sealed class CharactersViewState : MviViewState {

    object DefaultState : CharactersViewState()

    object LoadingState : CharactersViewState()

    object NoneCharactersState : CharactersViewState()

    class CharactersListState(val characters: List<UiCharacter>) : CharactersViewState()

    class FailureState(val error: Throwable): CharactersViewState()
}