package com.example.mvicompose.mvibase

import androidx.compose.runtime.Composable
import io.reactivex.Observable

interface MviView<I: MviIntent, S: MviViewState> {

    fun intents(): Observable<I>

    @Composable fun Render(state: S)
}