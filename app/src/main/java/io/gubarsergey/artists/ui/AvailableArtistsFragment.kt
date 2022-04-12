package io.gubarsergey.artists.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.databinding.FragmentArtistsBinding
import io.gubarsergey.verticalLinearLayoutManager

class AvailableArtistsFragment : BaseFragmentWithProps<FragmentArtistsBinding, AvailableArtistsProps>() {

    private val handler = Handler(Looper.getMainLooper())
    private val adapter = AvailableArtistsAdapter()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentArtistsBinding {
        return FragmentArtistsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({ propsSafe { viewLoaded() } }, 500)
        binding.availableArtistsRecycler.adapter = adapter
        binding.availableArtistsRecycler.verticalLinearLayoutManager()
    }

    override fun render(props: AvailableArtistsProps) {
        adapter.submitList(props.artists)
    }
}
