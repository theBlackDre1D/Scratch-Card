package co.init.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    protected lateinit var binding: VB

    protected val TAG
        get() = this.javaClass.name
}