package io.gubarsergey

import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import io.gubarsergey.redux.operations.Command

fun FragmentManager.inTransaction(block: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.block()
    transaction.commitAllowingStateLoss()
}

fun Button.click(command: Command) {
    this.setOnClickListener {
        command()
    }
}

fun EditText.bind(command: Command.With<String>): TextWatcher {
    return this.doOnTextChanged { text, start, before, count ->
        command.invoke(text.toString())
    }
}
