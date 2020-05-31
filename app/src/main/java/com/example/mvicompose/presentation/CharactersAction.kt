package com.example.mvicompose.presentation

import com.example.mvicompose.mvibase.MviAction

sealed class CharactersAction : MviAction {

    object LoadAllAction : CharactersAction()
}