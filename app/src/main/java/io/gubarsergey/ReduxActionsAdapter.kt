package io.gubarsergey

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.gubarsergey.databinding.ItemReduxActionBinding
import io.gubarsergey.redux.operations.Command

data class ReduxActionProps(
    val id: String,
    val timeStamp: String,
    val name: String,
    val apply: Command
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReduxActionProps

        if (id != other.id) return false
        if (timeStamp != other.timeStamp) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + timeStamp.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}

class ReduxActionsAdapter : ListAdapter<ReduxActionProps, ReduxActionsAdapter.ReduxActionViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReduxActionViewHolder {
        return ReduxActionViewHolder(ItemReduxActionBinding.inflate(parent.inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ReduxActionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReduxActionViewHolder(private val binding: ItemReduxActionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReduxActionProps) = with(binding) {
            actionNameTextView.text = item.name
            actionIdTextView.text = item.id
            actionTimeTextView.text = item.timeStamp
            root.setOnClickListener { item.apply() }
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReduxActionProps>() {
    override fun areItemsTheSame(oldItem: ReduxActionProps, newItem: ReduxActionProps): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReduxActionProps, newItem: ReduxActionProps): Boolean {
        return oldItem == newItem
    }
}
