package io.gubarsergey.orders

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.gubarsergey.BottomBarController
import io.gubarsergey.base.BaseFragment
import io.gubarsergey.databinding.FragmentOrdersCustomerBinding
import io.gubarsergey.verticalLinearLayoutManager
import timber.log.Timber

class OrdersFragment : BaseFragment<FragmentOrdersCustomerBinding, OrdersProps>() {

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
        }, 1000)
    }

    override fun render(props: OrdersProps) {
        Timber.d("render $props")
        adapter.submitList(props.orders)
    }
}
