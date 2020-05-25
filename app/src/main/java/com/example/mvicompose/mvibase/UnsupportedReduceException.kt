package com.example.mvicompose.mvibase

class UnsupportedReduceException(S: MviViewState, R: MviResult) :
    RuntimeException("Cannot reduce state: $S with result: $R")