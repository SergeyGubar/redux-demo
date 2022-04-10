package io.gubarsergey

sealed class Screen {
    object CustomerOrders : Screen()
}

interface RoutingOperations {
    fun goTo(destination: Screen)
}

object Router {

    lateinit var routingOperations: RoutingOperations

    fun goToOrdersCustomer() {
        routingOperations.goTo(Screen.CustomerOrders)
    }
}
