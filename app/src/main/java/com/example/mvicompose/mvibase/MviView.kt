package com.example.mvicompose.mvibase

import androidx.compose.runtime.Composable

interface MviView<I: MviIntent, S: MviViewState> {

    @Composable fun Render(state: S)
}