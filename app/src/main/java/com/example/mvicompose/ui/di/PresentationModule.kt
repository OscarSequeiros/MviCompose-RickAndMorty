package com.example.mvicompose.ui.di

import com.example.mvicompose.rx.SchedulerProvider
import com.example.mvicompose.rx.SchedulerProviderImpl
import dagger.Binds

abstract class PresentationModule {

    @Binds
    abstract fun bindScheduler(impl: SchedulerProviderImpl): SchedulerProvider
}