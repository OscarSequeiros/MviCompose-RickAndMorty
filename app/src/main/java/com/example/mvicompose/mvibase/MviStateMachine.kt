package com.example.mvicompose.mvibase

import arrow.core.Option

interface MviStateMachine<S: MviViewState, R: MviResult> {

    infix fun S.reduce(result: R): Option<S>
}