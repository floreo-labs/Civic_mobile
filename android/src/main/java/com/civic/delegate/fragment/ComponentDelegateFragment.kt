package com.civic.delegate.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.civic.delegate.ComponentDelegate

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        delegate.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        delegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}