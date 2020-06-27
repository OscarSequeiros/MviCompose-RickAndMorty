package com.example.mvicompose.presentation

import arrow.core.NonEmptyList
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.usecase.GetCharactersUseCase
import com.example.mvicompose.presentation.CharacterResult.*
import com.example.mvicompose.presentation.CharactersAction.*
import com.example.mvicompose.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class CharactersProcessorHolder @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val schedulerProvider: SchedulerProvider
) {

    val actionProcessor =
        ObservableTransformer<CharactersAction, CharacterResult> { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(LoadAllAction::class.java).compose(loadAllProcessor),
                    shared
                        .filter { action -> action !is LoadAllAction }
                        .flatMap { action ->
                            Observable.error<CharacterResult> {
                                IllegalArgumentException("unknown action type :$action")
                            }
                        }
                )
            }
        }

    private val loadAllProcessor =
        ObservableTransformer<LoadAllAction, LoadAllResult> { actions ->
            actions
                .flatMap {
                    getCharactersUseCase()
                        .toObservable()
                        .map(::defineSuccessResult)
                        .cast(LoadAllResult::class.java)
                        .onErrorReturn(LoadAllResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(LoadAllResult.Loading)
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