package com.example.mvicompose.ui.di

import com.example.mvicompose.data.CharacterDataRepository
import com.example.mvicompose.data.DataCharacterMapper
import com.example.mvicompose.data.remote.ApiClient
import com.example.mvicompose.data.remote.CharactersRemoteStore
import com.example.mvicompose.domain.repository.CharacterRepository
import com.example.mvicompose.domain.usecase.GetCharactersUseCase
import com.example.mvicompose.presentation.CharactersStateMachine
import com.example.mvicompose.presentation.CharactersViewModel
import com.example.mvicompose.presentation.mapper.UiCharacterMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
class DependenciesProvider {

    private val api = ApiClient().build()

    private val remoteStore = CharactersRemoteStore(api)

    private val dataMapper = DataCharacterMapper()

    private val repository: CharacterRepository = CharacterDataRepository(
        remoteStore = remoteStore,
        dataMapper = dataMapper
    )

    private val useCase = GetCharactersUseCase(repository)

    private val uiCharactersMapper = UiCharacterMapper()

    private val stateMachine = CharactersStateMachine(
        mapper = uiCharactersMapper
    )

    @ExperimentalCoroutinesApi
    fun instanceViewModel() = CharactersViewModel(
        getCharactersUseCase = useCase,
        stateMachine = stateMachine
    )
}