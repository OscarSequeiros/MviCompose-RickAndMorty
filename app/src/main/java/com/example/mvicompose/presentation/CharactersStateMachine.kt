package com.example.mvicompose.presentation

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.example.mvicompose.mvibase.MviStateMachine
import com.example.mvicompose.presentation.CharacterResult.LoadAllResult.*
import com.example.mvicompose.presentation.CharactersViewState.*
import com.example.mvicompose.presentation.mapper.UiCharacterMapper
import javax.inject.Inject

class CharactersStateMachine @Inject constructor(
    private val mapper: UiCharacterMapper
) : MviStateMachine<CharactersViewState, CharacterResult> {

    override fun CharactersViewState.reduce(result: CharacterResult): Option<CharactersViewState> {
        return when (val previousState = this) {
            is DefaultState         -> previousState + result
            is LoadingState         -> previousState + result
            is NoneCharactersState  -> previousState + result
            is CharactersListState  -> previousState + result
            is FailureState         -> previousState + result
        }
    }

    private operator fun DefaultState.plus(result: CharacterResult): Option<CharactersViewState> {
        return when (result) {
            Loading -> Some(LoadingState)
            else    -> None
        }
    }

    private operator fun LoadingState.plus(result: CharacterResult): Option<CharactersViewState> {
        return when (result) {
            is FilledCharacterList  -> Some(CharactersListState(mapper.map(result.characters)))
            is EmptyCharacterList   -> Some(NoneCharactersState)
            is Failure              -> Some(FailureState(result.error))
            else                    -> None
        }
    }

    private operator fun NoneCharactersState.plus(result: CharacterResult): Option<CharactersViewState> {
        return when (result) {
            Loading -> Some(LoadingState)
            else    -> None
        }
    }

    private operator fun CharactersListState.plus(result: CharacterResult): Option<CharactersViewState> {
        return when (result) {
            Loading -> Some(LoadingState)
            else    -> None
        }
    }

    private operator fun FailureState.plus(result: CharacterResult): Option<CharactersViewState> {
        return when (result) {
            Loading -> Some(LoadingState)
            else    -> None
        }
    }
}