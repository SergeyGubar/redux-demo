package io.gubarsergey.artists

import android.view.LayoutInflater
import android.view.ViewGroup
import io.gubarsergey.base.BaseFragment
import io.gubarsergey.databinding.FragmentArtistsBinding

object AvailableArtistsProps

class AvailableArtistsFragment: BaseFragment<FragmentArtistsBinding, AvailableArtistsProps>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentArtistsBinding {
        return FragmentArtistsBinding.inflate(inflater, container, false)
    }

    override fun render(props: AvailableArtistsProps) {
    }
}
