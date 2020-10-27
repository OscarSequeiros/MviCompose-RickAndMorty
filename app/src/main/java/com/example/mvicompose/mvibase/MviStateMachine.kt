package com.example.mvicompose.mvibase

import arrow.core.Option
import arrow.core.Some
import arrow.core.none
import java.lang.IllegalArgumentException

interface MviStateMachine<S: MviViewState, R: MviResult> {

    infix fun S.reduce(result: R): Option<S>
}