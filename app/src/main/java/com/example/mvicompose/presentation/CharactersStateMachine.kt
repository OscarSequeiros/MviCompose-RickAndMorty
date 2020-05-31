package com.example.mvicompose.presentation

import com.example.mvicompose.mvibase.MviStateMachine
import com.example.mvicompose.mvibase.UnsupportedReduceException
import com.example.mvicompose.presentation.CharacterResult.LoadAllResult.*
import com.example.mvicompose.presentation.CharactersViewState.*
import com.example.mvicompose.presentation.mapper.UiCharacterMapper

class CharactersStateMachine(
    private val mapper: UiCharacterMapper
) : MviStateMachine<CharactersViewState, CharacterResult> {

    override fun CharactersViewState.reduce(result: CharacterResult): CharactersViewState {
        return when (val previousState = this) {
            is DefaultState         -> previousState + result
            is LoadingState         -> previousState + result
            is NoneCharactersState  -> previousState + result
            is CharactersListState  -> previousState + result
            is FailureState         -> previousState + result
        }
    }

    private operator fun DefaultState.plus(result: CharacterResult): CharactersViewState {
        return when (result) {
            Loading -> LoadingState
            else    -> throw UnsupportedReduceException(this, result)
        }
    }

    private operator fun LoadingState.plus(result: CharacterResult): CharactersViewState {
        return when (result) {
            is FilledCharacterList  -> CharactersListState(mapper.map(result.characters))
            is EmptyCharacterList   -> NoneCharactersState
            is Failure              -> FailureState(result.error)
            else                    -> throw UnsupportedReduceException(this, result)
        }
    }

    private operator fun NoneCharactersState.plus(result: CharacterResult): CharactersViewState {
        return when (result) {
            Loading -> LoadingState
            else    -> throw UnsupportedReduceException(this, result)
        }
    }

    private operator fun CharactersListState.plus(result: CharacterResult): CharactersViewState {
        return when (result) {
            Loading -> LoadingState
            else    -> throw UnsupportedReduceException(this, result)
        }
    }

    private operator fun FailureState.plus(result: CharacterResult): CharactersViewState {
        return when (result) {
            Loading -> LoadingState
            else    -> throw UnsupportedReduceException(this, result)
        }
    }
}