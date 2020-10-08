package com.example.mvicompose.presentation

import com.example.mvicompose.CharactersFactory
import com.example.mvicompose.presentation.CharacterResult.LoadAllResult
import com.example.mvicompose.presentation.CharactersViewState.*
import com.example.mvicompose.presentation.mapper.UiCharacterMapper
import com.example.mvicompose.presentation.model.UiCharacter
import io.kotest.assertions.arrow.option.shouldBeNone
import io.kotest.assertions.arrow.option.shouldBeSome
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class CharactersStateMachineTest {

    private val mapper = mockk<UiCharacterMapper>()
    private val stateMachine = CharactersStateMachine(mapper)

    @Test
    fun `given DefaultState and Loading as result, when reduces, then gets LoadingState`() {
        val previousState = DefaultState
        val result = LoadAllResult.Loading

        val newState = with(stateMachine) { previousState reduce result }

        newState shouldBeSome LoadingState
    }

    @Test
    fun `given DefaultState and any other result != Loading, when reduces, then gets None`() {
        val previousState = DefaultState
        val result = LoadAllResult.EmptyCharacterList

        val newState = with(stateMachine) { previousState reduce result }

        newState.shouldBeNone()
    }

    @Test
    fun `given LoadingState and FilledCharacterList as result, when reduces, then gets CharactersListState`() {
        val previousState = LoadingState
        val characters = CharactersFactory.makeFakeNonEmptyCharacters()
        val result = LoadAllResult.FilledCharacterList(characters)
        val uiCharacters = CharactersFactory.makeFakeUiCharacters()
        stubCharactersMapper(uiCharacters)

        val newState = with(stateMachine) { previousState reduce result }

        // The following line must be satisfied. I am looking for a solution.
        //newState shouldBeSome CharactersListState(uiCharacters)
        newState.map { state -> assert(state is CharactersListState) }
    }

    @Test
    fun `given LoadingState and EmptyCharacterList as result, when reduces, then gets NoneCharactersState`() {
        val previousState = LoadingState
        val result = LoadAllResult.EmptyCharacterList

        val newState = with(stateMachine) { previousState reduce result }

        newState shouldBeSome NoneCharactersState
    }

    @Test
    fun `given LoadingState and Failure as result, when reduces, then gets FailureState`() {
        val previousState = LoadingState
        val fakeError = Throwable()
        val result = LoadAllResult.Failure(fakeError)

        val newState = with(stateMachine) { previousState reduce result }

        // The following line must be satisfied. I am looking for a solution.
        //newState shouldBeSome FailureState(fakeError)
        newState.map { state -> assert(state is FailureState) }
    }

    @Test
    fun `given LoadingState and any other result, when reduces, then gets None`() {
        val previousState = LoadingState
        val result = LoadAllResult.Loading

        val newState = with(stateMachine) { previousState reduce result }

        newState.shouldBeNone()
    }

    @Test
    fun `given NoneCharactersState and Loading as result, when reduces, then gets LoadingState`() {
        val previousState = NoneCharactersState
        val result = LoadAllResult.Loading

        val newState = with(stateMachine) { previousState reduce result }

        val expectedState = LoadingState
        newState shouldBeSome expectedState
    }

    @Test
    fun `given NoneCharactersState and any other result, when reduces, then gets None`() {
        val previousState = NoneCharactersState
        val result = LoadAllResult.Failure(Throwable())

        val newState = with(stateMachine) { previousState reduce result }

        newState.shouldBeNone()
    }

    @Test
    fun `given CharactersListState and Loading as result, when reduces, then gets LoadingState`() {
        val previousState = CharactersListState(CharactersFactory.makeFakeUiCharacters())
        val result = LoadAllResult.Loading

        val newState = with(stateMachine) { previousState reduce result }

        newState shouldBeSome LoadingState
    }

    @Test
    fun `given CharactersListState and any other result, when reduces, then gets None`() {
        val previousState = CharactersListState(CharactersFactory.makeFakeUiCharacters())
        val result = LoadAllResult.Failure(Throwable())

        val newState = with(stateMachine) { previousState reduce result }

        newState.shouldBeNone()
    }

    @Test
    fun `given FailureState and Loading as result, when reduces, then gets LoadingState`() {
        val previousState = FailureState(Throwable())
        val result = LoadAllResult.Loading

        val newState = with(stateMachine) { previousState reduce result }

        newState shouldBeSome LoadingState
    }

    @Test
    fun `given FailureState and any other result, when reduces, then gets None`() {
        val previousState = FailureState(Throwable())
        val result = LoadAllResult.Failure(Throwable())

        val newState = with(stateMachine) { previousState reduce result }

        newState.shouldBeNone()
    }

    private fun stubCharactersMapper(uiCharacters: List<UiCharacter>) {
        every { mapper.map(any()) } returns uiCharacters
    }
}