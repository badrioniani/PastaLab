package com.example.pastaorderapp.di

import com.example.pastaorderapp.data.CreateOrderUseCase
import com.example.pastaorderapp.data.GetExamplesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetExamplesUseCase(
        repository: ExampleRepository
    ): GetExamplesUseCase = GetExamplesUseCase(repository)

    @Provides
    @Singleton
    fun provideCreateOrderUseCase(
        repository: ExampleRepository
    ): CreateOrderUseCase {
        return CreateOrderUseCase(repository = repository)
    }
}
