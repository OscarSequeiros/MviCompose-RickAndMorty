package com.example.mvicompose.presentation

import com.example.mvicompose.CharactersFactory
import com.example.mvicompose.mvibase.UnsupportedReduceException
import com.example.mvicompose.presentation.CharacterResult.LoadAllResult
import com.example.mvicompose.presentation.CharactersViewState.*
import com.example.mvicompose.presentation.mapper.UiCharacterMapper
import org.junit.Test

class CharactersStateMachineTest {

    private val mapper = UiCharacterMapper()
    private val stateMachine = CharactersStateMachine(mapper)

    @Test
    fun `given DefaultState and Loading as result, when reduces, then gets LoadingState`() {
        val previousState = DefaultState
        val result = LoadAllResult.Loading

        val newState = with(stateMachine) { previousState reduce result }

        assert(newState is LoadingState)
    }

    @Test(expected = UnsupportedReduceException::class)
    fun `given DefaultState and any other result != Loading, when reduces, then gets UnsupportedReduceException`() {
        val previousState = DefaultState
        val result = LoadAllResult.EmptyCharacterList

        with(stateMachine) { previousState reduce result }
    }

    @Test
    fun `given LoadingState and FilledCharacterList as result, when reduces, then gets CharactersListState`() {
        val previousState = LoadingState
        val characters = CharactersFactory.makeFakeNonEmptyCharacters()
        val result = LoadAllResult.FilledCharacterList(characters)

        val newState = with(stateMachine) { previousState reduce result }

        assert(newState is CharactersListState)
    }

    @Test
    fun `given LoadingState and EmptyCharacterList as result, when reduces, then gets NoneCharactersState`() {
        val previousState = LoadingState
        val result = LoadAllResult.EmptyCharacterList

        val newState = with(stateMachine) { previousState reduce result }

        assert(newState is NoneCharactersState)
    }

    @Test
    fun `given LoadingState and Failure as result, when reduces, then gets FailureState`() {
        val previousState = LoadingState
        val result = LoadAllResult.Failure(Throwable())

        val newState = with(stateMachine) { previousState reduce result }

        assert(newState is FailureState)
    }

    @Test(expected = UnsupportedReduceException::class)
    fun `given LoadingState and any other result, when reduces, then gets UnsupportedReduceException`() {
        val previousState = LoadingState
        val result = LoadAllResult.Loading

        with(stateMachine) { previousState reduce result }
    }

    @Test
    fun `given NoneCharactersState and Loading as result, when reduces, then gets LoadingState`() {
        val previousState = NoneCharactersState
        val result = LoadAllResult.Loading

        val newState = with(stateMachine) { previousState reduce result }

        assert(newState is LoadingState)
    }

    @Test(expected = UnsupportedReduceException::class)
    fun `given NoneCharactersState and any other result, when reduces, then gets UnsupportedReduceException`() {
        val previousState = NoneCharactersState
        val result = LoadAllResult.Failure(Throwable())

        with(stateMachine) { previousState reduce result }
    }

    @Test
    fun `given CharactersListState and Loading as result, when reduces, then gets LoadingState`() {
        val previousState = CharactersListState(CharactersFactory.makeFakeUiCharacters())
        val result = LoadAllResult.Loading

        val newState = with(stateMachine) { previousState reduce result }

        assert(newState is LoadingState)
    }

    @Test(expected = UnsupportedReduceException::class)
    fun `given CharactersListState and any other result, when reduces, then gets UnsupportedReduceException`() {
        val previousState = CharactersListState(CharactersFactory.makeFakeUiCharacters())
        val result = LoadAllResult.Failure(Throwable())

        with(stateMachine) { previousState reduce result }
    }

    @Test
    fun `given FailureState and Loading as result, when reduces, then gets LoadingState`() {
        val previousState = FailureState(Throwable())
        val result = LoadAllResult.Loading

        val newState = with(stateMachine) { previousState reduce result }

        assert(newState is LoadingState)
    }

    @Test(expected = UnsupportedReduceException::class)
    fun `given FailureState and any other result, when reduces, then gets UnsupportedReduceException`() {
        val previousState = FailureState(Throwable())
        val result = LoadAllResult.Failure(Throwable())

        with(stateMachine) { previousState reduce result }
    }
}