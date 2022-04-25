package io.gubarsergey.artists.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import io.gubarsergey.R
import io.gubarsergey.artists.Genres
import io.gubarsergey.artists.ui.AvailableArtistsAdapter
import io.gubarsergey.base.BaseFragment
import io.gubarsergey.databinding.FragmentArtistsBinding
import io.gubarsergey.snackbar
import io.gubarsergey.verticalLinearLayoutManager
import org.koin.android.ext.android.inject

class AvailableArtistsMvvmFragment : BaseFragment<FragmentArtistsBinding>() {

    private val viewModel: AvailableArtistsViewModel by inject()
    private val adapter: AvailableArtistsAdapter = AvailableArtistsAdapter()

    private val chips by lazy {
        mapOf(
            Genres.RAP to binding.rapChip,
            Genres.HIP_HOP to binding.hipHopChip,
            Genres.FOLK to binding.folkChip,
            Genres.POP to binding.popChip,
            Genres.METAL to binding.metalChip,
        )
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentArtistsBinding =
        FragmentArtistsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.availableArtistsRecycler.adapter = adapter
        binding.availableArtistsRecycler.verticalLinearLayoutManager()

        binding.artistFiltersButton.setOnClickListener {
            val popUp = PopupMenu(requireContext(), binding.artistFiltersButton)
            popUp.menuInflater.inflate(R.menu.artists_filtering_menu, popUp.menu)
            popUp.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.best_rating -> viewModel.selectBestRatingFilter()
                    R.id.most_orders -> viewModel.selectMostOrdersFilter()
                    R.id.none        -> viewModel.selectNoneFilter()
                }
                true
            }
            popUp.show()
        }
        chips.forEach { (key, chip) ->
            chip.setOnClickListener {
                viewModel.selectChip(key)
            }
        }

        viewModel.artists.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.errors.observe(viewLifecycleOwner) {
            snackbar(R.string.general_network_error)
        }
        viewModel.sorting.observe(viewLifecycleOwner) {
            binding.artistFiltersButton.setText(
                when (it) {
                    AvailableArtistsViewModel.Sorting.NONE        -> R.string.none
                    AvailableArtistsViewModel.Sorting.BEST_RATING -> R.string.best_rating
                    AvailableArtistsViewModel.Sorting.MOST_ORDERS -> R.string.most_orders
                }
            )
        }
        viewModel.chipsSelected.observe(viewLifecycleOwner) { chipsSelected ->
            chips.forEach { (key, chip) ->
                chip.isSelected = key in chipsSelected
            }
        }
    }
}
