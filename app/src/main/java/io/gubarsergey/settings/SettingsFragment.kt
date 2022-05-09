package io.gubarsergey.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.click
import io.gubarsergey.databinding.FragmentSettingsBinding
import io.gubarsergey.redux.operations.Command

data class SettingsProps(
    val name: String,
    val email: String,
    val role: String,
    val logout: Command
)

class SettingsFragment : BaseFragmentWithProps<FragmentSettingsBinding, SettingsProps>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun render(props: SettingsProps) = with(binding) {
        emailValueTextView.text = props.email
        nameValueTextView.text = props.name
        roleValueTextView.text = props.role
        logoutButton.click(props.logout)
    }
}
