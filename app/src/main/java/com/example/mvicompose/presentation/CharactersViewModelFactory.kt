package com.example.mvicompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvicompose.ui.di.DependenciesProvider

class CharactersViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == CharactersViewModel::class.java) {
            return DependenciesProvider().instanceViewModel() as T
        } else {
            throw IllegalArgumentException("Unknown model class :$modelClass")
        }
    }
}