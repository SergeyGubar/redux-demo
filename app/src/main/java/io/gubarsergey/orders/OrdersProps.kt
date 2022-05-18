package io.gubarsergey.orders

import io.gubarsergey.auth.AuthState
import io.gubarsergey.redux.operations.Command

data class OrdersProps(
    val viewLoaded: Command,
    val orders: Orders
) {

    sealed class Orders {
        object Idle : Orders()
        object Loading : Orders()
        data class Loaded(val orders: List<Order>) : Orders()
        object LoadingFailed : Orders()
    }

    data class Order(
        val id: String,
        val artistName: String,
        val placedDate: String,
        val deadline: String,
        val status: OrderStatus,
        val comment: String,
        val bpm: String,
        val genres: List<String>,
        val goToDetails: Command,
        val userRole: AuthState.UserRole,
        val accept: Command,
        val reject: Command,
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Order

            if (id != other.id) return false
            if (artistName != other.artistName) return false
            if (placedDate != other.placedDate) return false
            if (deadline != other.deadline) return false
            if (status != other.status) return false
            if (comment != other.comment) return false
            if (bpm != other.bpm) return false
            if (genres != other.genres) return false
            if (userRole != other.userRole) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + artistName.hashCode()
            result = 31 * result + placedDate.hashCode()
            result = 31 * result + deadline.hashCode()
            result = 31 * result + status.hashCode()
            result = 31 * result + comment.hashCode()
            result = 31 * result + bpm.hashCode()
            result = 31 * result + genres.hashCode()
            result = 31 * result + userRole.hashCode()
            return result
        }
    }
}
