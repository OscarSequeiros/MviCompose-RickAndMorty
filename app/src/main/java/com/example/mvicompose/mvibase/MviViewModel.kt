package com.example.mvicompose.mvibase

import io.reactivex.Observable

interface MviViewModel<I: MviIntent, S: MviViewState> {

    fun processIntents(intents: Observable<I>)
}