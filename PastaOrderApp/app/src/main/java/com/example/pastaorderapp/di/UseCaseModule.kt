package com.example.pastaorderapp.di

import com.example.pastaorderapp.data.GetExamplesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetExamplesUseCase(
        repository: ExampleRepository
    ): GetExamplesUseCase = GetExamplesUseCase(repository)
}
