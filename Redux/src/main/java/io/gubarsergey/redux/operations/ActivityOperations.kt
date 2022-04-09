package io.gubarsergey.redux.operations

class Command(private val action: () -> Unit) {
    companion object {
        fun nop() = Command {}
    }

    operator fun invoke() = action()

    class With<T>(private val action: (T) -> Unit) {
        companion object {
            fun <T> nop() = With<T> {}
        }

        operator fun invoke(value: T) = action(value)

        fun bind(value: T) =
            Command { action(value) }
    }
}



interface ActivityOperations {
    fun showSnackbar(text: String?)
    fun showSnackbar(resId: Int)

    fun showSnackbarWithAction(
        mainTextStringId: Int,
        actionStringId: Int,
        command: Command
    )

    fun showDialog(type: DialogType)

    fun dispatchOnUiThread(action: () -> Unit)


    sealed class DialogType {

    }
}
