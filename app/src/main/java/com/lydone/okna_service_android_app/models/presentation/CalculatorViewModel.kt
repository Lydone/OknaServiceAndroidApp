package com.lydone.okna_service_android_app.models.presentation

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lydone.okna_service_android_app.domain.calculator.CalculatorInteractor
import com.lydone.okna_service_android_app.models.data.TestBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalculatorViewModel @ViewModelInject constructor(
    private val interactor: CalculatorInteractor,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun test() = interactor.testRequest().enqueue(object : Callback<List<TestBean>> {
        override fun onResponse(call: Call<List<TestBean>>, response: Response<List<TestBean>>) {
            Log.d("TAG", response.body().toString())
        }

        override fun onFailure(call: Call<List<TestBean>>, t: Throwable) {
            Log.e("TAG", "Error", t)
        }
    })

}