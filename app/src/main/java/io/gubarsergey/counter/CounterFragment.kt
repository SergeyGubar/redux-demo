package io.gubarsergey.counter

import android.view.LayoutInflater
import android.view.ViewGroup
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.databinding.FragmentCounterBinding
import io.gubarsergey.redux.operations.Command

data class CounterProps(
    val counter: Int,
    val increment: Command,
)

class CounterFragment : BaseFragmentWithProps<FragmentCounterBinding, CounterProps>() {

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCounterBinding {
        return FragmentCounterBinding.inflate(inflater, container, false)
    }

    override fun render(props: CounterProps) {
        binding.incrementButton.setOnClickListener {
            props.increment()
        }
        binding.counterTextView.text = "Counter: ${props.counter}"
    }
}
