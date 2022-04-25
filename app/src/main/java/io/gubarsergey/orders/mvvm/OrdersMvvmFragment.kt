package io.gubarsergey.orders.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.gubarsergey.BottomBarController
import io.gubarsergey.base.BaseFragment
import io.gubarsergey.databinding.FragmentOrdersCustomerBinding
import io.gubarsergey.orders.OrdersRecyclerAdapter
import io.gubarsergey.verticalLinearLayoutManager
import org.koin.android.ext.android.inject

class OrdersMvvmFragment : BaseFragment<FragmentOrdersCustomerBinding>() {

    private val viewModel: OrdersViewModel by inject()
    private val ordersAdapter = OrdersRecyclerAdapter()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOrdersCustomerBinding =
        FragmentOrdersCustomerBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myOrdersRecycler.adapter = ordersAdapter
        binding.myOrdersRecycler.verticalLinearLayoutManager()

        viewModel.orders.observe(viewLifecycleOwner) {
            ordersAdapter.submitList(it)
        }
        (context as? BottomBarController)?.showBottomBar()
    }
}
