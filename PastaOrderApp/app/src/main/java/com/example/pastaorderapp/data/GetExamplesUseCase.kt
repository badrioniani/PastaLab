package com.example.pastaorderapp.data

import com.example.pastaorderapp.di.ExampleRepository

class GetExamplesUseCase(
    private val repository: ExampleRepository
) {
    suspend operator fun invoke(): ExampleResponse {
        return repository.getExamples()
    }
}
