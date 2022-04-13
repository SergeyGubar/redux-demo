package io.gubarsergey.artists.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import io.gubarsergey.R
import io.gubarsergey.artists.Genres
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.databinding.FragmentArtistsBinding
import io.gubarsergey.verticalLinearLayoutManager

class AvailableArtistsFragment : BaseFragmentWithProps<FragmentArtistsBinding, AvailableArtistsProps>() {

    private val handler = Handler(Looper.getMainLooper())
    private val adapter = AvailableArtistsAdapter()

    private val chips by lazy {
        mapOf(
            Genres.RAP to binding.rapChip,
            Genres.HIP_HOP to binding.hipHopChip,
            Genres.FOLK to binding.folkChip,
            Genres.POP to binding.popChip,
            Genres.METAL to binding.metalChip,
        )
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentArtistsBinding {
        return FragmentArtistsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({ propsSafe { viewLoaded() } }, 300)
        binding.availableArtistsRecycler.adapter = adapter
        binding.availableArtistsRecycler.verticalLinearLayoutManager()
        binding.artistFiltersButton.setOnClickListener {
            val popUp = PopupMenu(requireContext(), binding.artistFiltersButton)
            popUp.menuInflater.inflate(R.menu.artists_filtering_menu, popUp.menu)
            popUp.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.best_rating -> propsSafe { selectBestRatingFilter() }
                    R.id.most_orders -> propsSafe { selectMostOrdersFilter() }
                    R.id.none        -> propsSafe { selectNoneFilter() }
                }
                true
            }
            popUp.show()
        }
    }

    override fun render(props: AvailableArtistsProps) = with(binding) {
        adapter.submitList(props.artists)
        props.chips.forEach { (key, entry) ->
            val chip = chips[key] ?: error("No chip $key")
            chip.isSelected = entry.isSelected
            chip.setOnClickListener {
                entry.click()
            }
        }
        artistFiltersButton.setText(
            when (props.filter) {
                AvailableArtistsProps.Filter.NONE        -> R.string.none
                AvailableArtistsProps.Filter.BEST_RATING -> R.string.best_rating
                AvailableArtistsProps.Filter.MOST_ORDERS -> R.string.most_orders
            }
        )
    }
}
