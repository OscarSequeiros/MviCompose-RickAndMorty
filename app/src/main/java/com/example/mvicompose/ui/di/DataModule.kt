package com.example.mvicompose.ui.di

import com.example.mvicompose.data.remote.ApiClient
import com.example.mvicompose.data.remote.RickAndMortyApi
import com.example.mvicompose.rx.SchedulerProvider
import com.example.mvicompose.rx.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
object DataModule {

    @Provides
    fun providesApi(): RickAndMortyApi = ApiClient().build()
}