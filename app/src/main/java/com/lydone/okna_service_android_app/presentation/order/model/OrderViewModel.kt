package com.lydone.okna_service_android_app.presentation.order.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.okna_service_android_app.domain.interactor.OrderInteractor
import com.lydone.okna_service_android_app.domain.model.Order
import com.lydone.okna_service_android_app.presentation.core.SingleLiveEvent
import com.lydone.okna_service_android_app.presentation.core.State
import com.lydone.okna_service_android_app.presentation.core.StateLiveData
import com.lydone.okna_service_android_app.presentation.core.StateMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val interactor: OrderInteractor) : ViewModel() {

    private val orderStateMutableLiveData = StateMutableLiveData<Order>()
    val orderStateLiveData: StateLiveData<Order> get() = orderStateMutableLiveData

    private val urlMutableLiveData = SingleLiveEvent<String>()
    val urlLiveData: LiveData<String> get() = urlMutableLiveData

    var id: Int? = null
        set(value) {
            if (value != null) {
                field = value
                loadOrder(value)
            }
        }

    private fun loadOrder(id: Int) {
        viewModelScope.launch {
            orderStateMutableLiveData.setLoadingState()
            orderStateMutableLiveData.setSuccessState(interactor.getOrder(id))
        }
    }

    fun refreshOrder() {
        id?.let { loadOrder(it) }
    }

    fun onPayButtonClicked() {
        viewModelScope.launch {
            val isPrepayment = (orderStateMutableLiveData.value as State.Success).data.status == Order.Status.CREATED
            orderStateMutableLiveData.setLoadingState()
            urlMutableLiveData.value = interactor.getPaymentUrl(
                orderId = requireNotNull(id),
                isPrepayment = isPrepayment
            )
        }
    }
}