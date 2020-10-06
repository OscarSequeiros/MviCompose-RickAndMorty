package com.example.mvicompose.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.NonEmptyList
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.usecase.GetCharactersUseCase
import com.example.mvicompose.mvibase.MviViewModel
import com.example.mvicompose.presentation.CharacterResult.LoadAllResult
import com.example.mvicompose.presentation.CharactersAction.LoadAllAction
import com.example.mvicompose.presentation.CharactersIntent.*
import com.example.mvicompose.presentation.CharactersViewState.DefaultState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class CharactersViewModel @ViewModelInject constructor (
    private val getCharactersUseCase: GetCharactersUseCase,
    private val stateMachine: CharactersStateMachine
) : ViewModel(), MviViewModel<CharactersIntent, CharactersViewState> {

    private val intentsChannel = BroadcastChannel<CharactersIntent>(Channel.BUFFERED)

    private val _state = MutableStateFlow<CharactersViewState>(DefaultState)

    val state: StateFlow<CharactersViewState>
        get() = _state


    init {
        intentsChannel
            .asFlow()
            .map { intent -> intent.toAction() }
            .process()
            .scan(DefaultState as CharactersViewState) { state, result ->
                with (stateMachine) { state reduce result }
            }
            .onEach { newState -> _state.value = newState }
            .launchIn(viewModelScope)
    }

    private fun CharactersIntent.toAction(): CharactersAction {
        return when (this) {
            LoadAllIntent, RetryLoadAllIntent, RefreshAllIntent -> LoadAllAction
        }
    }

    private fun Flow<CharactersAction>.process(): Flow<CharacterResult> {
        val getCharacters =
            flow { emit(getCharactersUseCase()) }
            .map { characters -> defineSuccessResult(characters) }
            .onStart { emit(LoadAllResult.Loading) }
            .catch { emit(LoadAllResult.Failure(it)) }

        return merge(
            filterIsInstance<LoadAllAction>().flatMapConcat { getCharacters }
        )
    }

    private fun defineSuccessResult(characters: List<Character>): LoadAllResult {
        return if (characters.isEmpty()) {
            LoadAllResult.EmptyCharacterList
        } else {
            LoadAllResult.FilledCharacterList(NonEmptyList.fromListUnsafe(characters))
        }
    }

    override fun processIntent(intent: CharactersIntent) {
        intentsChannel.offer(intent)
    }
}