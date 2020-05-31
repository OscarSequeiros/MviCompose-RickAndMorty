package com.example.mvicompose.presentation

import com.example.mvicompose.mvibase.MviIntent

sealed class CharactersIntent : MviIntent {

    object LoadAllIntent : CharactersIntent()

    object RetryLoadAllIntent : CharactersIntent()

    object RefreshAllIntent : CharactersIntent()
}