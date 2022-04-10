package io.gubarsergey.orders

import io.gubarsergey.redux.operations.Command

data class OrdersProps(
    val viewLoaded: Command,
    val orders: List<Order>
) {
    data class Order(
        val id: String,
        val artistName: String,
        val placedDate: String,
        val deadline: String,
        val status: OrderStatus,
        val comment: String,
        val bpm: String,
        val genres: List<String>,
        val goToDetails: Command
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
            return result
        }
    }
}
