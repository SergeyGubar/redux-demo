package io.gubarsergey.orders

sealed class OrderStatus {

    companion object {
        fun fromString(str: String) = when (str) {
            "PLACED"                -> Placed
            "IN_PROGRESS"           -> InProgress
            "REJECTED"              -> Rejected
            "AWAITING_CONFIRMATION" -> AwaitingConfirmation
            "DONE"                  -> Completed
            else                    -> error("Unknown order status $str")
        }
    }

    object Placed : OrderStatus()
    object InProgress : OrderStatus()
    object Rejected : OrderStatus()
    object AwaitingConfirmation : OrderStatus()
    object Completed : OrderStatus()

    override fun toString(): String {
        return when (this) {
            AwaitingConfirmation -> "Awaiting Confirmation"
            Completed            -> "Completed"
            InProgress           -> "In Progress"
            Placed               -> "Placed"
            Rejected             -> "Rejected"
        }
    }

    fun toNetworkString(): String {
        return when (this) {
            AwaitingConfirmation -> "AWAITING_CONFIRMATION"
            Completed            -> "DONE"
            InProgress           -> "IN_PROGRESS"
            Placed               -> "PLACED"
            Rejected             -> "REJECTED"
        }
    }
}
