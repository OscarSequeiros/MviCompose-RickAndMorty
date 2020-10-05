package com.example.mvicompose.presentation

import com.example.mvicompose.CharactersFactory
import com.example.mvicompose.CoroutineTestRule
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.usecase.GetCharactersUseCase
import com.example.mvicompose.presentation.CharactersIntent.LoadAllIntent
import com.example.mvicompose.presentation.CharactersIntent.RetryLoadAllIntent
import com.example.mvicompose.presentation.CharactersViewState.*
import com.example.mvicompose.presentation.mapper.UiCharacterMapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.yield
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class CharactersViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val mapper = mockk<UiCharacterMapper>()
    private val stateMachine = CharactersStateMachine(mapper)
    private val useCase = mockk<GetCharactersUseCase>()

    private val viewModel = CharactersViewModel(
        getCharactersUseCase = useCase,
        stateMachine = stateMachine
    )

    private val characters = CharactersFactory.makeFakeCharacters()
    private val uiCharacters = CharactersFactory.makeFakeUiCharacters()

    @Ignore("Looking for how to test StateFlow")
    @Test
    fun `given characters from use case, when process LoadAllIntent, gets CharactersListState`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            stubUseCase(characters)
            stubMapper()

            viewModel.processIntent(LoadAllIntent)

            val states = getStates()

            assert(states[0] is DefaultState)
            assert(states[1] is LoadingState)
            assert(states[2] is CharactersListState)
        }

    @Ignore("Looking for how to test StateFlow")
    @Test
    fun `given no characters from use case, when process LoadAllIntent, gets NoneCharactersState`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            stubUseCase(emptyList())
            stubMapper()

            viewModel.processIntent(LoadAllIntent)

            val states = getStates()

            assert(states[0] is DefaultState)
            assert(states[1] is LoadingState)
            assert(states[2] is NoneCharactersState)
        }

    @Ignore("Looking for how to test StateFlow")
    @Test
    fun `given error from use case, when process LoadAllIntent, gets FailureState`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            stubErrorUseCase()
            stubMapper()

            viewModel.processIntent(LoadAllIntent)

            val states = getStates()

            assert(states[0] is DefaultState)
            assert(states[1] is LoadingState)
            assert(states[2] is FailureState)
        }

    @Ignore("Looking for how to test StateFlow")
    @Test
    fun `given characters from use case, when process LoadAllIntent and RetryLoadAllIntent, gets CharactersListState`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            stubUseCase(characters)
            stubMapper()

            viewModel.processIntent(LoadAllIntent)
            viewModel.processIntent(RetryLoadAllIntent)

            val states = getStates()

            assert(states[0] is DefaultState)
            assert(states[1] is LoadingState)
            assert(states[2] is CharactersListState)
            assert(states[3] is DefaultState)
            assert(states[4] is CharactersListState)
        }


    private suspend fun getStates() =
        viewModel
            .state
            .onEach { yield() }
            .toList()

    private fun stubUseCase(characters: List<Character>) {
        coEvery { useCase.invoke() } coAnswers { characters }
    }

    private fun stubErrorUseCase() {
        coEvery { useCase.invoke() } coAnswers { error("") }
    }

    private fun stubMapper() {
        every { mapper.map(any()) } returns uiCharacters
    }
}