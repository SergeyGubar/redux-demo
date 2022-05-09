package io.gubarsergey.orders

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.gubarsergey.R
import io.gubarsergey.auth.AuthState
import io.gubarsergey.click
import io.gubarsergey.databinding.ItemOrderCustomerBinding
import io.gubarsergey.inflater
import io.gubarsergey.visibleIf

class OrdersRecyclerAdapter : ListAdapter<OrdersProps.Order, OrdersRecyclerAdapter.OrderViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ItemOrderCustomerBinding.inflate(parent.inflater, parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrderViewHolder(private val binding: ItemOrderCustomerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(props: OrdersProps.Order) = with(binding) {
            placedByTextView.text = when (props.userRole) {
                AuthState.UserRole.ARTIST   -> binding.root.context.getString(R.string.placed_from)
                AuthState.UserRole.CUSTOMER -> binding.root.context.getString(R.string.placed_for)
            }
            placedByValueTextView.text = props.artistName
            datePlacedTextView.text = "Placed on: ${props.placedDate.substring(0, props.placedDate.indexOf("T"))}"
            deadlineTextView.text = "Deadline: ${props.deadline.substring(0, props.deadline.indexOf("T"))}"
            bpmTextView.text = props.bpm
            genresTextView.text = props.genres.joinToString(" ")
            commentTextView.text = props.comment
            statusTextView.text = props.status.toString()
            acceptRejectContainer.visibleIf(props.userRole == AuthState.UserRole.ARTIST && props.status == OrderStatus.Placed)
            approveButton.click(props.accept)
            rejectButton.click(props.reject)
            root.setOnClickListener { props.goToDetails() }
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OrdersProps.Order>() {
    override fun areItemsTheSame(oldItem: OrdersProps.Order, newItem: OrdersProps.Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrdersProps.Order, newItem: OrdersProps.Order): Boolean {
        return oldItem == newItem
    }
}
