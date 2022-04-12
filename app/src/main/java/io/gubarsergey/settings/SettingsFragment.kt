package io.gubarsergey.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.databinding.FragmentSettingsBinding

object SettingsProps

class SettingsFragment: BaseFragmentWithProps<FragmentSettingsBinding, SettingsProps>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun render(props: SettingsProps) {
    }
}
