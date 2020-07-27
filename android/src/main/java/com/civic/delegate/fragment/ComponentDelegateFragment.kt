package com.civic.delegate.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.civic.delegate.ComponentDelegate
import com.civic.di.extensions.loadModule
import com.civic.di.extensions.unloadModule
import org.koin.android.ext.android.getKoin
import org.koin.core.module.Module

abstract class ComponentDelegateFragment : Fragment() {

    abstract val delegate: ComponentDelegate

    private lateinit var module: Module

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun createModule(): Module

    override fun onAttach(context: Context) {
        module = createModule()
        getKoin().loadModule(module)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutId, container, false).also { view ->
            delegate.bind(view, savedInstanceState)
        }

    override fun onDestroy() {
        super.onDestroy()

        getKoin().unloadModule(module)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        delegate.unbind()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        delegate.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        delegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}