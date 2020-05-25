package com.example.mvicompose.mvibase

interface MviStateMachine<S: MviViewState, R: MviResult> {

    infix fun S.reduce(result: R): S
}