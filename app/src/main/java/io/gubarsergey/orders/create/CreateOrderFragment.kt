package io.gubarsergey.orders.create

import android.annotation.SuppressLint
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.slider.Slider
import io.gubarsergey.R
import io.gubarsergey.artists.Genres
import io.gubarsergey.base.BaseFragmentWithProps
import io.gubarsergey.click
import io.gubarsergey.databinding.FragmentCreateOrderBinding
import io.gubarsergey.hide
import io.gubarsergey.redux.operations.Command
import io.gubarsergey.show
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed interface CreateOrderProps {
    data class Loaded(
        val artistName: String,
        val chips: Map<String, ChipInfo>,
        val deadline: String?,
        val makeAnOrder: Command,
        val bpmUpdated: Command.With<Int>,
        val commentUpdated: Command.With<String>,
        val deadlineUpdated: Command.With<String>,
    ) : CreateOrderProps {
        data class ChipInfo(
            val isSelected: Boolean,
            val select: Command,
            val title: String,
        )
    }

    object NotAvailable : CreateOrderProps
}

class CreateOrderFragment : BaseFragmentWithProps<FragmentCreateOrderBinding, CreateOrderProps>() {

    private val chips by lazy {
        mapOf(
            Genres.RAP to binding.rapChip,
            Genres.HIP_HOP to binding.hipHopChip,
            Genres.FOLK to binding.folkChip,
            Genres.POP to binding.popChip,
            Genres.METAL to binding.metalChip,
        )
    }
    private var touchListener: Slider.OnSliderTouchListener? = null
    private var textChangeListener: TextWatcher? = null

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCreateOrderBinding {
        return FragmentCreateOrderBinding.inflate(inflater, container, false)
    }

    override fun render(props: CreateOrderProps) = with(binding) {
        when (props) {
            is CreateOrderProps.Loaded    -> {
                customizeOrderForArtist.text = getString(R.string.your_order_for, props.artistName)
                props.chips.forEach { (name, info) ->
                    val chip = chips[name] ?: return@forEach
                    chip.isSelected = info.isSelected
                    chip.click(info.select)
                }
                props.deadline?.let {
                    deadlineButton.text = it.substring(0, it.indexOf("T"))
                }

                if (touchListener == null) {
                    touchListener = object : Slider.OnSliderTouchListener {
                        @SuppressLint("RestrictedApi")
                        override fun onStartTrackingTouch(slider: Slider) {
                        }

                        @SuppressLint("RestrictedApi")
                        override fun onStopTrackingTouch(slider: Slider) {
                            props.bpmUpdated(slider.value.toInt())
                        }
                    }
                    bpmSeekbar.addOnSliderTouchListener(touchListener!!)
                }
                if (textChangeListener == null) {
                    textChangeListener = commentEditText.doOnTextChanged { text, start, before, count ->
                        props.commentUpdated(text.toString())
                    }
                }

                deadlineButton.setOnClickListener {
                    val datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select deadline")
                        .build()
                    datePicker.addOnPositiveButtonClickListener { date ->
                        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                        props.deadlineUpdated(format.format(Date(date)))
                    }
                    datePicker.show(parentFragmentManager, "DATE")
                }
                saveButton.click(props.makeAnOrder)

                genresTextView.show()
                genresContainer.show()
                bpmSeekbar.show()
                bpmTextView.show()
                commentInput.show()
                deadlineButton.show()
                saveButton.show()
            }
            CreateOrderProps.NotAvailable -> {
                customizeOrderForArtist.text = getString(R.string.data_not_available)
                genresTextView.hide()
                genresContainer.hide()
                bpmSeekbar.hide()
                commentInput.hide()
                deadlineButton.hide()
                saveButton.hide()
                bpmTextView.hide()
            }
        }
    }
}
