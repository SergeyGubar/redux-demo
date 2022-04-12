package io.gubarsergey.base
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentWithProps<BINDING : ViewBinding, PROPS> : Fragment() {

    private var _binding: BINDING? = null
    protected val binding get() = _binding!!

    val props: MutableLiveData<PROPS> = MutableLiveData()

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getFragmentBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        props.observe(viewLifecycleOwner, ::render)
    }

    abstract fun render(props: PROPS)

    fun propsSafe(block: PROPS.() -> Unit) {
        props.value?.let(block)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
