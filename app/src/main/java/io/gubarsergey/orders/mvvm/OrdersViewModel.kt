package io.gubarsergey.orders.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.gubarsergey.auth.mvvm.PrefHelper
import io.gubarsergey.orders.OrderStatus
import io.gubarsergey.orders.OrdersProps
import io.gubarsergey.orders.service.MyOrdersDto
import io.gubarsergey.orders.service.OrdersAPI
import io.gubarsergey.redux.operations.Command
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrdersViewModel(
    private val api: OrdersAPI,
    private val prefHelper: PrefHelper,
) : ViewModel() {

    private val _orders: MutableLiveData<List<OrdersProps.Order>> = MutableLiveData()
    val orders: LiveData<List<OrdersProps.Order>> = _orders

    private val _errors: MutableLiveData<String> = MutableLiveData()
    val errors: LiveData<String> = _errors

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    api.myOrders(prefHelper.getToken()?.bearerFormatted ?: "")
                }
            }
            result.fold(
                onSuccess = { _orders.value = it.toUi() },
                onFailure = { }
            )
        }
    }

    private fun MyOrdersDto.toUi(): List<OrdersProps.Order> {
        return orders.map {
            OrdersProps.Order(
                id = it.order._id,
                artistName = it.to.name + " " + it.to.lastName,
                placedDate = it.order.datePlaced,
                deadline = it.order.deadline,
                status = OrderStatus.fromString(it.order.status),
                comment = it.order.comment,
                bpm = it.order.bpm,
                genres = it.order.genre,
                goToDetails = Command(
                    action = {})
            )
        }
    }
}
