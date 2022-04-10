package io.gubarsergey

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

fun RecyclerView.verticalLinearLayoutManager() {
    this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
}

val ViewGroup.inflater get() = LayoutInflater.from(this.context)

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}
