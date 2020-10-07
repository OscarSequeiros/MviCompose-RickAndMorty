package com.example.mvicompose.ui.di

import com.example.mvicompose.data.CharacterDataRepository
import com.example.mvicompose.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun CharacterDataRepository.bind(): CharacterRepository
}