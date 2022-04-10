package io.gubarsergey.orders

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.gubarsergey.databinding.ItemOrderCustomerBinding
import io.gubarsergey.inflater

class OrdersRecyclerAdapter : ListAdapter<OrdersProps.Order, OrdersRecyclerAdapter.OrderViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ItemOrderCustomerBinding.inflate(parent.inflater, parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrderViewHolder(private val binding: ItemOrderCustomerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrdersProps.Order) = with(binding) {
            placedByValueTextView.text = item.artistName
            datePlacedTextView.text = item.placedDate
            deadlineTextView.text = item.deadline
            bpmTextView.text = item.bpm
            genresTextView.text = item.genres.joinToString(" ")
            commentTextView.text = item.comment
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
