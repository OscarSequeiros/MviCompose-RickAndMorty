package com.example.mvicompose.presentation

import arrow.core.Option
import arrow.core.getOrElse
import com.example.mvicompose.mvibase.MviViewState
import com.example.mvicompose.presentation.model.UiCharacter

sealed class CharactersViewState : MviViewState {

    object DefaultState : CharactersViewState()

    object LoadingState : CharactersViewState()

    object NoneCharactersState : CharactersViewState()

    class CharactersListState(val characters: List<UiCharacter>) : CharactersViewState()

    class FailureState(val error: Throwable): CharactersViewState()

    companion object {

        fun Option<CharactersViewState>.getOrDefault() = getOrElse { DefaultState }

         val defaultAsOption: Option<CharactersViewState>
            get() = Option(DefaultState)
    }
}