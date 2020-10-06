package com.example.mvicompose.mvibase

import androidx.compose.runtime.Composable
import io.reactivex.Observable

interface MviView<I: MviIntent, S: MviViewState> {

    @Composable fun Render(state: S)
}