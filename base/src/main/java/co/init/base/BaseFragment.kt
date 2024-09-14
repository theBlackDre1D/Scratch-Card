package co.init.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    protected lateinit var binding: VB

    protected val TAG
        get() = this.javaClass.name

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerOnBackPressCallback()
    }

    open fun onBackPressed() { }

    private fun registerOnBackPressCallback() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()

                // If you want to navigate back to the previous fragment
                isEnabled = false // Disable this callback to allow default back press behavior
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        })
    }
}