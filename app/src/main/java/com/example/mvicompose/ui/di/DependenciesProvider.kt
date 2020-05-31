package com.example.mvicompose.ui.di

import com.example.mvicompose.data.CharacterDataRepository
import com.example.mvicompose.data.DataCharacterMapper
import com.example.mvicompose.data.remote.ApiClient
import com.example.mvicompose.data.remote.CharactersRemoteStore
import com.example.mvicompose.domain.repository.CharacterRepository
import com.example.mvicompose.domain.usecase.GetCharactersUseCase
import com.example.mvicompose.presentation.CharactersProcessorHolder
import com.example.mvicompose.presentation.CharactersStateMachine
import com.example.mvicompose.presentation.CharactersViewModel
import com.example.mvicompose.presentation.mapper.UiCharacterMapper
import com.example.mvicompose.rx.SchedulerProvider
import com.example.mvicompose.rx.SchedulerProviderImpl

class DependenciesProvider {

    private val api = ApiClient().build()

    private val remoteStore = CharactersRemoteStore(api)

    private val dataMapper = DataCharacterMapper()

    private val repository: CharacterRepository = CharacterDataRepository(
        remoteStore = remoteStore,
        dataMapper = dataMapper
    )

    private val useCase = GetCharactersUseCase(repository)

    private val schedulerProvider: SchedulerProvider = SchedulerProviderImpl()

    private val processorHolder = CharactersProcessorHolder(
        getCharactersUseCase = useCase,
        schedulerProvider = schedulerProvider
    )

    private val uiCharactersMapper = UiCharacterMapper()

    private val stateMachine = CharactersStateMachine(
        mapper = uiCharactersMapper
    )

    fun instanceViewModel() = CharactersViewModel(
        actionProcessor = processorHolder,
        stateMachine = stateMachine
    )
}