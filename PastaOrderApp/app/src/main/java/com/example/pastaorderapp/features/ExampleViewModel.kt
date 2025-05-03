package com.example.pastaorderapp.features

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastaorderapp.data.CreateOrderUseCase
import com.example.pastaorderapp.data.ExampleResponse
import com.example.pastaorderapp.data.GetExamplesUseCase
import com.example.pastaorderapp.data.OrderRequest
import com.example.pastaorderapp.features.model.OrderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val getExamplesUseCase: GetExamplesUseCase,
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel() {

    private val _examples = MutableLiveData<ExampleResponse>()
    val examples: LiveData<ExampleResponse> = _examples


    private val _order = MutableLiveData<Any>()
    val order: LiveData<Any> = _order

    init {
        fetchExamples()
    }

    fun sendOrder(token: String,request: OrderRequest) {
        viewModelScope.launch {
            try {
                _order.value = createOrderUseCase(token,request)
            } catch (e: Exception) {
                Log.e("ExampleVM", "Error: ${e.message}")
            }
        }
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
