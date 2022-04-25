package io.gubarsergey.auth.ui

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.gubarsergey.BottomBarController
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.bind
import io.gubarsergey.click
import io.gubarsergey.databinding.FragmentAuthBinding
import io.gubarsergey.redux.operations.Command
import io.gubarsergey.setTextIfChanged

data class AuthProps(
    val email: String,
    val password: String,
    val login: Command,
    val emailChanged: Command.With<String>,
    val passwordChanged: Command.With<String>,
)

class AuthFragment : BaseFragmentWithProps<FragmentAuthBinding, AuthProps>() {

    private var emailWatcher: TextWatcher? = null
    private var passwordWatcher: TextWatcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BottomBarController)?.hideBottomBar()
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

    override fun render(props: AuthProps) = with(binding) {
        emailWatcher?.let { emailEditText.removeTextChangedListener(it) }
        passwordWatcher?.let { passwordEditText.removeTextChangedListener(it) }

        emailEditText.setTextIfChanged(props.email)
        passwordEditText.setTextIfChanged(props.password)

        emailWatcher = emailEditText.bind(props.emailChanged)
        passwordWatcher = passwordEditText.bind(props.passwordChanged)

        loginButton.click(props.login)
    }
}
