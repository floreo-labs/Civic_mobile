package thc.civic.delegate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import thc.civic.R
import thc.civic.delegate.ComponentDelegate

abstract class ComponentDelegateFragment : Fragment() {

    abstract val delegate: ComponentDelegate

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutId, container, false).also { view ->
            delegate.bind(view, savedInstanceState)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        delegate.unbind()
    }
}