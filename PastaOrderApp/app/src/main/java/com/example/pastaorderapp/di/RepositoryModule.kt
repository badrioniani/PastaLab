package com.example.pastaorderapp.di

import com.example.pastaorderapp.data.SyrveApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideExampleRepository(
        apiService: SyrveApiService
    ): ExampleRepository = ExampleRepositoryImpl(apiService)
}
