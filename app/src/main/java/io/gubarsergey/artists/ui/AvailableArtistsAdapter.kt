package io.gubarsergey.artists.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.gubarsergey.databinding.ItemArtistBinding
import io.gubarsergey.inflater

class AvailableArtistsAdapter : ListAdapter<AvailableArtistsProps.ArtistProps, AvailableArtistsAdapter.ArtistViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return ArtistViewHolder(ItemArtistBinding.inflate(parent.inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ArtistViewHolder(private val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AvailableArtistsProps.ArtistProps) = with(binding) {
            artistNameTextView.text = item.fullName
            genresTextView.text = item.genres.joinToString(separator = " ")
            averageRatingValueTextView.text = item.averageRating.toString()
            ratingsCountValueTextView.text = item.ratingCount.toString()
            profileDescriptionTextView.text = item.profileDescription
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AvailableArtistsProps.ArtistProps>() {
    override fun areItemsTheSame(oldItem: AvailableArtistsProps.ArtistProps, newItem: AvailableArtistsProps.ArtistProps): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AvailableArtistsProps.ArtistProps, newItem: AvailableArtistsProps.ArtistProps): Boolean {
        return oldItem == newItem
    }
}
