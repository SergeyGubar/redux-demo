package io.gubarsergey.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import io.gubarsergey.base.BaseFragment
import io.gubarsergey.databinding.FragmentSettingsBinding

object SettingsProps

class SettingsFragment: BaseFragment<FragmentSettingsBinding, SettingsProps>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun render(props: SettingsProps) {
    }
}
