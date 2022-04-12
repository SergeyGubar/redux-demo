package io.gubarsergey.base
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentWithMenu<BINDING : ViewBinding, PROPS> : BaseFragmentWithProps<BINDING, PROPS>() {

    abstract val menuResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(menuResource, menu)
    }
}
