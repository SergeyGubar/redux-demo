package io.gubarsergey.orders

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import io.gubarsergey.BottomBarController
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.databinding.FragmentOrdersCustomerBinding
import io.gubarsergey.verticalLinearLayoutManager

class OrdersFragment : BaseFragmentWithProps<FragmentOrdersCustomerBinding, OrdersProps>() {

    private val handler = Handler(Looper.getMainLooper())
    private val adapter = OrdersRecyclerAdapter()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOrdersCustomerBinding {
        return FragmentOrdersCustomerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BottomBarController)?.showBottomBar()

        binding.myOrdersRecycler.adapter = adapter
        binding.myOrdersRecycler.verticalLinearLayoutManager()

        handler.postDelayed({
            props.value?.viewLoaded?.invoke()
        }, 300)
    }

    override fun render(props: OrdersProps) = with(binding) {
        swipeRefreshLayout.setOnRefreshListener {
            props.viewLoaded()
        }
        when (props.orders) {
            OrdersProps.Orders.Idle          -> {
                myOrdersRecycler.isVisible = true
                swipeRefreshLayout.isVisible = true
                errorTextView.isVisible = false
            }
            is OrdersProps.Orders.Loaded     -> {
                myOrdersRecycler.isVisible = true
                swipeRefreshLayout.isRefreshing = false
                adapter.submitList(props.orders.orders.reversed())
                errorTextView.isVisible = false
            }
            OrdersProps.Orders.Loading       -> {
                myOrdersRecycler.isVisible = false
                swipeRefreshLayout.isRefreshing = true
                errorTextView.isVisible = false
            }
            OrdersProps.Orders.LoadingFailed -> {
                myOrdersRecycler.isVisible = false
                swipeRefreshLayout.isRefreshing = false
                errorTextView.isVisible = true
            }
        }

    }
}
