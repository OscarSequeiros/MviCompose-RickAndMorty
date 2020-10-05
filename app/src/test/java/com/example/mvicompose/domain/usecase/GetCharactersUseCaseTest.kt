package com.example.mvicompose.domain.usecase

import com.example.mvicompose.CharactersFactory
import com.example.mvicompose.CoroutineTestRule
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharactersUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val repository = mockk<CharacterRepository>()

    private val useCase = GetCharactersUseCase(repository)

    @Test
    fun `given List-Character, when invoke, then return same list`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
        val fakeCharacters = CharactersFactory.makeFakeCharacters(5)
        stubRepository(fakeCharacters)

        val characters = useCase.invoke()

        assert(characters == fakeCharacters)
    }

    private fun stubRepository(characters: List<Character>) {
        coEvery { repository.getAll() } coAnswers {
            characters
        }
    }
}