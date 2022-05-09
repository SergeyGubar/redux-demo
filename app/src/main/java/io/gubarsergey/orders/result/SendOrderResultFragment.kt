package io.gubarsergey.orders.result

import android.view.LayoutInflater
import android.view.ViewGroup
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.click
import io.gubarsergey.databinding.FragmentSendOrderResultBinding
import io.gubarsergey.redux.operations.Command

data class SendOrderResultProps(
    val send: Command.With<String>,
    val from: String,
    val description: String,
)

class SendOrderResultFragment : BaseFragmentWithProps<FragmentSendOrderResultBinding, SendOrderResultProps>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSendOrderResultBinding {
        return FragmentSendOrderResultBinding.inflate(inflater, container, false)
    }

    override fun render(props: SendOrderResultProps) = with(binding) {
        orderFromTextView.text = "Order from: ${props.from}\nCustomer comment: ${props.description}"
        sendToCustomerButton.click(Command { props.send(resultEditText.text.toString()) })
    }
}
