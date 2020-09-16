package com.example.mvicompose.presentation

import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.usecase.GetCharactersUseCase
import com.example.mvicompose.presentation.mapper.UiCharacterMapper
import com.example.mvicompose.rx.FakeScheduleProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test

class CharactersViewModelTest {

    private val mapper = mockk<UiCharacterMapper>()
    private val stateMachine = CharactersStateMachine(mapper)
    private val useCase = mockk<GetCharactersUseCase>()
    private val processor = CharactersProcessorHolder(
        getCharactersUseCase = useCase,
        schedulerProvider = FakeScheduleProvider()
    )

    private val viewModel = CharactersViewModel(
        actionProcessor = processor,
        stateMachine = stateMachine
    )

    private val characters = makeFakeCharacters().toList()
    private val uiCharacters = makeFakeUiCharacters()

    private lateinit var testObserver: TestObserver<CharactersViewState>

    @Before
    fun setUp() {
        testObserver = viewModel.statesObservable.test()
    }

    @Test
    fun `given characters from use case, when process LoadAllIntent, gets CharactersListState`() {
        stubUseCase(characters)
        stubMapper()
        viewModel.processIntents(Observable.just(CharactersIntent.LoadAllIntent))


        testObserver.assertValueAt(0) { it is CharactersViewState.DefaultState }
        testObserver.assertValueAt(1) { it is CharactersViewState.LoadingState }
        testObserver.assertValueAt(2) { it is CharactersViewState.CharactersListState }
    }

    @Test
    fun `given no characters from use case, when process LoadAllIntent, gets NoneCharactersState`() {
        stubUseCase(emptyList())
        stubMapper()
        viewModel.processIntents(Observable.just(CharactersIntent.LoadAllIntent))

        testObserver.assertValueAt(0) { it is CharactersViewState.DefaultState }
        testObserver.assertValueAt(1) { it is CharactersViewState.LoadingState }
        testObserver.assertValueAt(1) { it is CharactersViewState.NoneCharactersState }
    }

    @Test
    fun `given error from use case, when process LoadAllIntent, gets FailureState`() {
        stubErrorUseCase()
        stubMapper()
        viewModel.processIntents(Observable.just(CharactersIntent.LoadAllIntent))

        testObserver.assertValueAt(0) { it is CharactersViewState.DefaultState }
        testObserver.assertValueAt(1) { it is CharactersViewState.LoadingState }
        testObserver.assertValueAt(2) { it is CharactersViewState.FailureState }
    }

    @Test
    fun `given characters from use case, when process LoadAllIntent and RetryLoadAllIntent, gets CharactersListState`() {
        stubUseCase(characters)
        stubMapper()

        viewModel.processIntents(Observable.create { emitter ->
            emitter.onNext(CharactersIntent.LoadAllIntent)
            emitter.onNext(CharactersIntent.RetryLoadAllIntent)
            emitter.onComplete()
        })

        testObserver.assertValueAt(0) { it is CharactersViewState.DefaultState }
        testObserver.assertValueAt(1) { it is CharactersViewState.LoadingState }
        testObserver.assertValueAt(2) { it is CharactersViewState.CharactersListState }
        testObserver.assertValueAt(3) { it is CharactersViewState.LoadingState }
        testObserver.assertValueAt(4) { it is CharactersViewState.CharactersListState }
    }

    private fun stubUseCase(characters: List<Character>) {
        coEvery { useCase.invoke() } coAnswers {
            delay(1000)
            characters
        }
    }

    private fun stubErrorUseCase() {
        coEvery { useCase.invoke() } coAnswers {
            delay(1000)
            throw Throwable("")
        }
    }

    private fun stubMapper() {
        every { mapper.map(any()) } returns uiCharacters
    }
}