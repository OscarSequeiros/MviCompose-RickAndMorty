package com.example.mvicompose.presentation

import arrow.core.NonEmptyList
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.usecase.GetCharactersUseCase
import com.example.mvicompose.presentation.CharacterResult.*
import com.example.mvicompose.presentation.CharactersAction.*
import com.example.mvicompose.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import kotlinx.coroutines.*

class CharactersProcessorHolder(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val schedulerProvider: SchedulerProvider
) {

    private val processorJob = Job()
    private val coroutineScope = CoroutineScope(processorJob + Dispatchers.IO)

    val actionProcessor =
        ObservableTransformer<CharactersAction, CharacterResult> { actions ->
            actions.ofType(LoadAllAction::class.java).compose(loadAllProcessor)
        }

    private val loadAllProcessor =
        ObservableTransformer<LoadAllAction, LoadAllResult> { actions ->
            actions
                .flatMap {
                    Observable.create<LoadAllResult> { source ->
                        source.onNext(LoadAllResult.Loading)

                        val errorHandler = CoroutineExceptionHandler { _, exception ->
                            source.onNext(LoadAllResult.Failure(exception))
                        }

                        coroutineScope.launch(errorHandler) {
                            val characters = getCharactersUseCase()
                            source.onNext(defineSuccessResult(characters))
                        }
                    }
                        .observeOn(schedulerProvider.ui())
                }
        }

    private fun defineSuccessResult(characters: List<Character>): LoadAllResult {
        return if (characters.isEmpty()) {
            LoadAllResult.EmptyCharacterList
        } else {
            LoadAllResult.FilledCharacterList(NonEmptyList.fromListUnsafe(characters))
        }
    }
}