package com.example.mvicompose.mvibase


interface MviViewModel<I: MviIntent, S: MviViewState> {

    fun processIntent(intent: I)
}