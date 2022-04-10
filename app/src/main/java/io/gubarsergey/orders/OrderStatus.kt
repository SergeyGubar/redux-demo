package io.gubarsergey.orders

sealed class OrderStatus {

    companion object {
        fun fromString(str: String) = when (str) {
            "PLACED" -> Placed
            "IN_PROGRESS" -> InProgress
            "REJECTED" -> Rejected
            "AWAITING_CONFIRMATION" -> AwaitingConfirmation
            "DONE" -> Completed
            else -> error("Unknown order status $str")
        }
    }

    object Placed: OrderStatus()
    object InProgress: OrderStatus()
    object Rejected: OrderStatus()
    object AwaitingConfirmation: OrderStatus()
    object Completed: OrderStatus()
}
