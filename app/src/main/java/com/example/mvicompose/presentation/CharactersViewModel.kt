package com.example.mvicompose.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvicompose.mvibase.MviViewModel
import com.example.mvicompose.presentation.CharactersAction.*
import com.example.mvicompose.presentation.CharactersIntent.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

class CharactersViewModel(
    private val actionProcessor: CharactersProcessorHolder,
    private val stateMachine: CharactersStateMachine
) : ViewModel(), MviViewModel<CharactersIntent, CharactersViewState> {

    private val intentsSubject: PublishSubject<CharactersIntent> = PublishSubject.create()

    val statesObservable: Observable<CharactersViewState> = compose()

    private val liveDataStates: MutableLiveData<CharactersViewState> = MutableLiveData()

    private val disposable = CompositeDisposable()

    fun liveData(): LiveData<CharactersViewState> = liveDataStates

    init {
        disposable.add(statesObservable.subscribe(liveDataStates::setValue) {})
    }

    private fun compose(): Observable<CharactersViewState> {
        return intentsSubject
            .map(::mapFromIntentToAction)
            .compose(actionProcessor.actionProcessor)
            .scan(CharactersViewState.DefaultState, reducer())
    }

    private fun reducer(): BiFunction<CharactersViewState, CharacterResult, CharactersViewState> =
        BiFunction { previousState: CharactersViewState, result: CharacterResult ->
            with(stateMachine) { previousState reduce result }
        }

    private fun mapFromIntentToAction(intent: CharactersIntent): CharactersAction {
        return when (intent) {
            LoadAllIntent, RetryLoadAllIntent, RefreshAllIntent -> LoadAllAction
        }
    }

    override fun processIntents(intents: Observable<CharactersIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}