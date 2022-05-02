package io.gubarsergey

sealed class Screen {
    object CustomerOrders : Screen()
    object CreateOrder : Screen()
}

interface RoutingOperations {
    fun goTo(destination: Screen)
    fun goBack()
}

object Router {

    lateinit var routingOperations: RoutingOperations

    fun goToOrdersCustomer() {
        routingOperations.goTo(Screen.CustomerOrders)
    }

    fun goToCreateOrder() {
        routingOperations.goTo(Screen.CreateOrder)
    }

    fun goBack() {
        routingOperations.goBack()
    }
}
