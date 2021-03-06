package io.gubarsergey.auth.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import io.gubarsergey.BottomBarController
import io.gubarsergey.base.BaseFragment
import io.gubarsergey.databinding.FragmentAuthBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragmentMvvm : BaseFragment<FragmentAuthBinding>() {

    object GoToMyOrders

    private val viewModel: AuthViewModel by viewModel()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        viewModel.navigationEvents.observe(viewLifecycleOwner) {
            findNavController().navigate(AuthFragmentMvvmDirections.goToOrdersMvvm())
        }
        (context as? BottomBarController)?.hideBottomBar()
    }

    private fun setupListeners() {
        binding.emailEditText.doOnTextChanged { text, start, before, count ->
            viewModel.emailUpdated(text.toString())
        }
        binding.passwordEditText.doOnTextChanged { text, start, before, count ->
            viewModel.passwordUpdated(text.toString())
        }
        binding.loginButton.setOnClickListener {
            viewModel.login()
        }
    }
}
