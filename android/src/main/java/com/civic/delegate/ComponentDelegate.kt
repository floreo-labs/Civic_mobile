package com.civic.delegate

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes

abstract class ComponentDelegate {

    private val viewPropertyDelegateRegistry: MutableList<ViewPropertyDelegate<*>> = mutableListOf()

    protected open fun onViewAttached(savedState: Bundle?) {

    }

    protected fun <ViewType: View> register(@IdRes viewId: Int) =
        ViewPropertyDelegate<ViewType>(viewId).also {
            viewPropertyDelegateRegistry.add(it)
        }

    fun bindViews(root: View, savedState: Bundle?) {
        viewPropertyDelegateRegistry.forEach { viewPropertyDelegate ->
            viewPropertyDelegate.resolve(root)
        }
        onViewAttached(savedState)
    }

    open fun onViewDetached() {
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