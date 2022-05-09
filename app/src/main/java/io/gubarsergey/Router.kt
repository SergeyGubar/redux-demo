package io.gubarsergey

sealed class Screen {
    object CustomerOrders : Screen()
    object CreateOrder : Screen()
    object OrderConfirmation : Screen()
    object SendOrderResult : Screen()
    object Login : Screen()
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

    fun goToOrderConfirmation() {
        routingOperations.goTo(Screen.OrderConfirmation)
    }

    fun goToSendOrderResult() {
        routingOperations.goTo(Screen.SendOrderResult)
    }

    fun goToLogin() {
        routingOperations.goTo(Screen.Login)
    }

    fun goBack() {
        routingOperations.goBack()
    }
}
