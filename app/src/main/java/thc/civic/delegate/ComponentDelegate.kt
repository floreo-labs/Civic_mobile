package thc.civic.delegate

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes

abstract class ComponentDelegate {

    private val viewPropertyDelegateRegistry: MutableList<ViewPropertyDelegate<*>> = mutableListOf()

    protected open fun onViewsResolved(savedState: Bundle?) {

    }

    protected fun <ViewType: View> register(@IdRes viewId: Int) =
        ViewPropertyDelegate<ViewType>(viewId).also {
            viewPropertyDelegateRegistry.add(it)
        }

    fun bind(root: View, savedState: Bundle?) {
        viewPropertyDelegateRegistry.forEach { viewPropertyDelegate ->
            viewPropertyDelegate.resolve(root)
        }
        onViewsResolved(savedState)
    }

    fun unbind() {
        viewPropertyDelegateRegistry.forEach { viewPropertyDelegate ->
            viewPropertyDelegate.clear()
        }
        viewPropertyDelegateRegistry.clear()
    }
}