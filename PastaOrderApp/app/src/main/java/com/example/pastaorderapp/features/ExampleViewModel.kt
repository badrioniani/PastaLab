package com.example.pastaorderapp.features

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastaorderapp.data.ExampleResponse
import com.example.pastaorderapp.data.GetExamplesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val getExamplesUseCase: GetExamplesUseCase
) : ViewModel() {

    private val _examples = MutableLiveData<ExampleResponse>()
    val examples: LiveData<ExampleResponse> = _examples

    init {
        fetchExamples()
    }

    private fun fetchExamples() {
        viewModelScope.launch {
            try {
                _examples.value = getExamplesUseCase()
            } catch (e: Exception) {
                Log.e("ExampleVM", "Error: ${e.message}")
            }
        }
    }
}
