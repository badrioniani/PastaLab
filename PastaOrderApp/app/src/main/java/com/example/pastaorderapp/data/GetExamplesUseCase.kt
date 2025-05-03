package com.example.pastaorderapp.data

import com.example.pastaorderapp.di.ExampleRepository
import javax.inject.Inject

class GetExamplesUseCase @Inject constructor(
    private val repository: ExampleRepository
) {
    suspend operator fun invoke(): ExampleResponse {
        return repository.getExamples()
    }
}
