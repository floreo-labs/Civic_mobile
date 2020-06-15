package com.civic.delegate

import android.content.Intent
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

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    open fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

    }
}