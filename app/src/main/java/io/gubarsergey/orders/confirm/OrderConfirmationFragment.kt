package io.gubarsergey.orders.confirm

import android.os.Bundle
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.click
import io.gubarsergey.databinding.FragmentConfirmOrderBinding
import io.gubarsergey.redux.operations.Command

data class OrderConfirmationProps(
    val resultUrl: String?,
    val orderDescription: String,
    val orderTo: String,
    val rating: Int,
    val confirmOrder: Command,
    val rejectOrder: Command,
    val changeRating: Command.With<Int>,
    val changeComment: Command.With<String>,
)

class OrderConfirmationFragment : BaseFragmentWithProps<FragmentConfirmOrderBinding, OrderConfirmationProps>() {

    private var commentTextWatcher: TextWatcher? = null

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentConfirmOrderBinding {
        return FragmentConfirmOrderBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ratingView.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            propsSafe { changeRating(rating.toInt()) }
        }
    }

    override fun render(props: OrderConfirmationProps) = with(binding) {
        resultTextView.text = Html.fromHtml("Result: ${props.resultUrl}", Html.FROM_HTML_MODE_COMPACT)
        orderDescriptionTextView.text = "Order to: ${props.orderTo}\nDescription: ${props.orderDescription}"
        if (commentTextWatcher == null) {
            commentTextWatcher = commentEditText.doOnTextChanged { text, start, before, count ->
                props.changeComment(text.toString())
            }
        }
        approveButton.click(props.confirmOrder)
        rejectButton.click(props.rejectOrder)
    }
}
